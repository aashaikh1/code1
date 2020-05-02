package com.arif;

import com.arif.car.jpa.CarStock;
import com.arif.car.jpa.Car;
import com.arif.car.jpa.Photo;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author arif
 */
@Local
public interface CarsRepo {
    public List<CarStock> getFeaturedCars();
    public List<CarStock> getFeaturedCars(int pageNum);
    public long getFeaturedCarsCount();
    public boolean isLastPageForFeaturedCars(int pageNum);
    public List<CarStock> getSearchedCars(int pageNum, CarSearchCriteria searchCriteria);
    public CarStock getCarStock(String stockId);
    public long getSearchedCarsCount(CarSearchCriteria searchCriteria);
    public boolean isLastPageForSearchedCars(int pageNum, CarSearchCriteria searchCriteria);
    public void addCarStock(CarStock carStock);
    public List<Car> getCarTypes();
    public void updateCarStock(CarStock carStock);
    public void savePhotos(List<byte[]> photos, String carStockId);
    public Photo getPhoto(String photoObjKey);
    public void deletePhoto(String photoObjKey);
    public List<CarStock> getUserCars(int pageNum, String cognitoUserName);
    public boolean isLastPageForUserCars(int pageNum, String cognitoUserName);
    public long getUserCarsCount(String cognitoUserName);
    public void deleteCarStock(String stockId);
}
