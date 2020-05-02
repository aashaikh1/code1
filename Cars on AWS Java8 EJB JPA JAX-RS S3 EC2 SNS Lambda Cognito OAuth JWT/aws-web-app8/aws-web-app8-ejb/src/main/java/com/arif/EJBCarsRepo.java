package com.arif;

import com.arif.util.EventType;
import com.arif.util.QueryBuilder;
import com.arif.util.Util;
import com.arif.car.jpa.Car;
import com.arif.car.jpa.CarStock;
import com.arif.car.jpa.Photo;
import com.arif.util.EventFactory;
import com.arif.util.JsonEventFactory;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author arif
 */
@Stateless(name = "EJBCarsRepo")
public class EJBCarsRepo implements CarsRepo{

    //@PersistenceContext(unitName = "cars")
    private EntityManager em;
    
    //@EJB(beanName = "AwsS3BucketRepo", mappedName = "AwsS3BucketRepo")
    private AwsS3BucketRepoLocal awsS3BucketClient;
    
    //@EJB(beanName = "SNSEvents")
    private SNSEventsLocal awsSNS;    
    
    private EventFactory<String> eventFactory = new JsonEventFactory();
    
    private static final int RESULTS_PER_PAGE = 5;

    @PersistenceContext(unitName = "cars")
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @EJB(beanName = "AwsS3BucketRepo", mappedName = "AwsS3BucketRepo")
    public void setAwsS3BucketClient(AwsS3BucketRepoLocal awsS3BucketClient) {
        this.awsS3BucketClient = awsS3BucketClient;
    }

    @EJB(beanName = "SNSEvents")
    public void setAwsSNS(SNSEventsLocal awsSNS) {
        this.awsSNS = awsSNS;
    }

    public List<CarStock> getFeaturedCars(){
        TypedQuery<CarStock> featuredCarsQuery = em.createNamedQuery("CarStock.findByFeaturerd", CarStock.class);
        return featuredCarsQuery.getResultList();
    }
    
    public List<CarStock> getFeaturedCars(int pageNum){
        TypedQuery<CarStock> featuredCarsQuery = em.createNamedQuery("CarStock.findByFeaturerd", CarStock.class);
        List<CarStock> featuredCars = featuredCarsQuery
                .setFirstResult(pageNum*RESULTS_PER_PAGE)
                .setMaxResults(RESULTS_PER_PAGE)
                .getResultList();
        awsS3BucketClient.updateListWithObjectPreSignedUrl(featuredCars);
        return featuredCars;
    }
    
    public long getFeaturedCarsCount(){
        TypedQuery<Long> featuredCarsCountQuery = em.createNamedQuery("CarStock.countByFeaturerd", Long.class);
        return featuredCarsCountQuery.getSingleResult();
    }

    public static int getRESULTS_PER_PAGE() {
        return RESULTS_PER_PAGE;
    }
    
    public boolean isLastPageForFeaturedCars(int pageNum){
        if(pageNum*RESULTS_PER_PAGE > getFeaturedCarsCount() ){
            return true;
        }
        return false;
    }

    public List<CarStock> getSearchedCars(int pageNum, CarSearchCriteria searchCriteria){
        QueryBuilder.Statement stmt = QueryBuilder.buildSearchCarsQuery(searchCriteria);
        
        TypedQuery<CarStock> searchedCarsQuery = em.createQuery(stmt.getQuery(), CarStock.class);
        QueryBuilder.setStockCarQueryParameters(searchedCarsQuery, stmt, searchCriteria);
        
        List<CarStock> searchedCars = searchedCarsQuery
                .setFirstResult(pageNum*RESULTS_PER_PAGE)
                .setMaxResults(RESULTS_PER_PAGE)
                .getResultList();
        awsS3BucketClient.updateListWithObjectPreSignedUrl(searchedCars);
        return searchedCars;
    }
    
    public CarStock getCarStock(String stockId){
        CarStock car = em.find(CarStock.class, stockId);
        awsS3BucketClient.updateWithObjectPreSignedUrl(car);
        return car;
    }
    
    public boolean isLastPageForSearchedCars(int pageNum, CarSearchCriteria searchCriteria){
        if(pageNum*RESULTS_PER_PAGE > getSearchedCarsCount(searchCriteria) ){
            return true;
        }
        return false;        
    }

    public long getSearchedCarsCount(CarSearchCriteria searchCriteria){
        QueryBuilder.Statement stmt = QueryBuilder.buildCountSearchCarsQuery(searchCriteria);
        TypedQuery<Long> countSearchedCarsQuery = em.createQuery(stmt.getQuery(), Long.class);
        QueryBuilder.setStockCarQueryParameters(countSearchedCarsQuery, stmt, searchCriteria);
        return countSearchedCarsQuery.getSingleResult();
    }
    
    public void addCarStock(CarStock carStock){
        TypedQuery<Car> CarTypeQuery = em.createNamedQuery("Car.findByMakeAndModel", Car.class);
        CarTypeQuery.setParameter("make", carStock.getCar().getMake())
                .setParameter("model", carStock.getCar().getModel());
        Car carType = CarTypeQuery.getSingleResult();
        carStock.setStockId(UUID.randomUUID().toString());
        carStock.setCar(carType);
        carType.getCarStocks().add(carStock);
        em.persist(carStock);
        em.flush();
        event(eventFactory.buildEvent(carStock, EventType.ADDCAR));
    }

    
    public void updateCarStock(CarStock carStockUpdated){
        TypedQuery<Car> CarTypeQuery = em.createNamedQuery("Car.findByMakeAndModel", Car.class);
        CarTypeQuery.setParameter("make", carStockUpdated.getCar().getMake())
                .setParameter("model", carStockUpdated.getCar().getModel());
        System.out.println(carStockUpdated);
        Car carType = CarTypeQuery.getSingleResult();
        
        CarStock savedCarStock = getCarStock(carStockUpdated.getStockId());
        savedCarStock.getCar().getCarStocks().remove(savedCarStock);
        Util.copyBasicValues(carStockUpdated, savedCarStock);
        savedCarStock.setCar(carType);
        carType.getCarStocks().add(savedCarStock);
        em.flush();
        event(eventFactory.buildEvent(carStockUpdated, EventType.UPDATECAR));
    }

   
    public List<Car> getCarTypes(){
        return em.createNamedQuery("Car.findAll", Car.class).getResultList();
    }
    
    public void savePhotos(List<byte[]> photos, String carStockId){
        Photo photo;
        CarStock carStock = getCarStock(carStockId);
        List<String> uploadedPhotos = awsS3BucketClient.putPhotos(photos);
        for(String photoObjKey:uploadedPhotos){
            photo = new Photo();
            photo.setObjectKey(photoObjKey);
            photo.setStockId(carStock);
            carStock.getPhotos().add(photo);
            em.persist(photo);
        }
        em.flush();
        event(eventFactory.buildEvent(carStock, EventType.ADDPHOTO));
    }

    public Photo getPhoto(String photoObjKey){
        return em.find(Photo.class, photoObjKey);
    }
    
    public void deletePhoto(String photoObjKey){
        Photo photo = getPhoto(photoObjKey);
        CarStock carStock = photo.getStockId();
        carStock.getPhotos().remove(photo);
        em.remove(photo);
        em.flush();
        awsS3BucketClient.deletePhoto(photoObjKey);
        event(eventFactory.buildEvent(carStock, EventType.DELETEPHOTO));
    }
    
    public List<CarStock> getUserCars(int pageNum, String cognitoUserName){
        TypedQuery<CarStock> userCarsQuery = em.createNamedQuery("CarStock.findByCognitoUsername", CarStock.class);
        userCarsQuery.setParameter("cognitoUsername", cognitoUserName);
                
        List<CarStock> userCars = userCarsQuery
                .setFirstResult(pageNum*RESULTS_PER_PAGE)
                .setMaxResults(RESULTS_PER_PAGE)
                .getResultList();
        awsS3BucketClient.updateListWithObjectPreSignedUrl(userCars);
        return userCars;
    }
    
    public boolean isLastPageForUserCars(int pageNum, String cognitoUserName){
        if(pageNum*RESULTS_PER_PAGE > getUserCarsCount(cognitoUserName) ){
            return true;
        }
        return false;        
    }

    public long getUserCarsCount(String cognitoUserName){
        TypedQuery<Long> countUserCarsQuery = em.createNamedQuery("CarStock.countByCognitoUsername", Long.class);
        countUserCarsQuery.setParameter("cognitoUsername", cognitoUserName);
        return countUserCarsQuery.getSingleResult();
    }

    public void deleteCarStock(String stockId){
        CarStock carStock = getCarStock(stockId);
        List<String> photoObjKeys = null;
        if(carStock.getPhotos() != null && !carStock.getPhotos().isEmpty()){
            photoObjKeys = carStock.getPhotos().stream()
                    .map(p -> p.getObjectKey())
                    .collect(Collectors.toList());
        }
        em.remove(carStock);
        em.flush();
        if(photoObjKeys != null && !photoObjKeys.isEmpty()){
            photoObjKeys.stream().forEach(k -> awsS3BucketClient.deletePhoto(k));
        }
        event(eventFactory.buildEvent(carStock, EventType.DELETECAR));
    }

    private void event(String event){
        awsSNS.send(event);
    }
}
