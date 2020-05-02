package com.arif;

import com.arif.car.jpa.CarStock;
import com.arif.car.jpa.Car;
import com.arif.car.jpa.Photo;
import com.arif.util.AppUtil;
import com.arif.util.ExceptionWrapper;
import com.google.gson.Gson;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.jose4j.jwt.JwtClaims;

/**
 * 
 *
 * @author arif
 */
@Path("v1")
@RequestScoped
public class V1Resource {
    
    @Context
    private UriInfo context;
    
    @Context
    private HttpServletRequest request;
    @Context
    private HttpServletResponse response;
    
    @EJB(beanName = "EJBCarsRepo")
    private CarsRepo carsRepo;
    
    @EJB(beanName = "SNSNotification")
    private SNSNotificationLocal awsSNS;
    
    
    @GET
    @Path("/prev")
    public void prevFeaturedCars() {
        try {
            notify("prevFeaturedCars");
            int pageNum = Integer.parseInt(request.getParameter("fcCurrPageNum") == null ? "0" : request.getParameter("fcCurrPageNum"));
            if(pageNum > 0){
                pageNum = pageNum -1;
            }
            List<CarStock> featuredCars = carsRepo.getFeaturedCars(pageNum);
            request.setAttribute("featuredCars", featuredCars);
            request.setAttribute("featuredCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    @GET
    @Path("/next")
    public void nextFeaturedCars() {
        try {
            notify("nextFeaturedCars");
            int pageNum = Integer.parseInt(request.getParameter("fcCurrPageNum") == null ? "0" : request.getParameter("fcCurrPageNum"));
            if(!carsRepo.isLastPageForFeaturedCars(pageNum)){
                pageNum = pageNum + 1;
            }
            List<CarStock> featuredCars = carsRepo.getFeaturedCars(pageNum);
            request.setAttribute("featuredCars", featuredCars);
            request.setAttribute("featuredCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    
    
    @POST
    @Path("/search")
    public void searchCars() {
        try {
            notify("searchCars");
            int pageNum = 0;
            CarSearchCriteria searchCriteria = AppUtil.extractRequestParams(request);
            System.out.println(searchCriteria);
            List<CarStock> featuredCars = carsRepo.getSearchedCars(pageNum, searchCriteria);
            request.setAttribute("searchResultCars", featuredCars);
            request.setAttribute("searchedCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/WEB-INF/searchResults.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    
    
    @POST
    @Path("/prevSearch")
    public void prevSearchedCars() {
        try {
            notify("prevSearchedCars");
            int pageNum = Integer.parseInt(request.getParameter("scCurrPageNum") == null ? "0" : request.getParameter("scCurrPageNum"));
            if(pageNum > 0){
                pageNum = pageNum -1;
            }
            CarSearchCriteria searchCriteria = AppUtil.extractRequestParams(request);
            System.out.println(searchCriteria);            
            List<CarStock> searchedCars = carsRepo.getSearchedCars(pageNum, searchCriteria);
            request.setAttribute("searchResultCars", searchedCars);
            request.setAttribute("searchedCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/WEB-INF/searchResults.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    @POST
    @Path("/nextSearch")
    public void nextSearchedCars() {
        try {
            notify("nextSearchedCars");
            int pageNum = Integer.parseInt(request.getParameter("scCurrPageNum") == null ? "0" : request.getParameter("scCurrPageNum"));
            CarSearchCriteria searchCriteria = AppUtil.extractRequestParams(request);
            System.out.println(searchCriteria);            
            if(!carsRepo.isLastPageForSearchedCars(pageNum, searchCriteria)){
                pageNum = pageNum + 1;
            }
            List<CarStock> searchedCars = carsRepo.getSearchedCars(pageNum, searchCriteria);
            request.setAttribute("searchResultCars", searchedCars);
            request.setAttribute("searchedCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/WEB-INF/searchResults.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    
    @GET
    @Path("/cardetail/{carStockId}")
    public void getCarDetails(@PathParam("carStockId") String carStockId) {
        try {
            notify("getCarDetails");
            CarStock carStock = carsRepo.getCarStock(carStockId);
            request.setAttribute("carStock", carStock);
            request.getRequestDispatcher("/WEB-INF/carDetails.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    
    @GET
    @Path("/login")
    public void login() {
        try {
            notify("login");
            String csrf_state = AppUtil.getCSRFString();
            request.getSession(true).setAttribute("csrf_state", csrf_state);
            String location = AppContext.getInstance().getCOGNITO_DOMAIN_LOGIN() + "?response_type=code&client_id=" +
                    AppContext.getInstance().getCLIENT_ID() + 
                    "&state=" + csrf_state +
                    "&redirect_uri=" + AppContext.getInstance().getCALLBACK_URL();
            response.sendRedirect(location);
        } catch (Exception e) {
            handelError(e);
        } 
    }    


    @GET
    @Path("/callback")
    public void callback() {
        try {
            notify("callback");
            String csrf_state = request.getParameter("state");
            String code = request.getParameter("code");
            
            if(!csrf_state.equals(getSessionAttributeString("csrf_state"))){
                request.getRequestDispatcher("/CSRF-Mismatch.jsp").forward(request, response);
                return;
            }

            Map<String, String> tokenMap = AwsCognitoOAuthHelper.getCognitoTokens(code);

            JwtClaims claims = AwsCognitoOAuthHelper.getJWTClaims(tokenMap.get("id_token"));

            AppUtil.setUserInSession(request.getSession(false), claims.getClaimsMap());//todo potential NPE
            response.sendRedirect(AppContext.getInstance().getHOME_URL());
        } catch (Exception e) {
            handelError(e);
        } 
    }    
    
    
    @GET
    @Path("/logout")
    public void logout() {
        try {
            notify("logout");
            String location="";
            location = AppContext.getInstance().getCOGNITO_DOMAIN_LOGOUT() + "?response_type=code&client_id="
                    + AppContext.getInstance().getCLIENT_ID()
                    + "&logout_uri=" + AppContext.getInstance().getLOGOUT_URL();
            if(request.getSession(false) != null){
                request.getSession(false).invalidate();
            }
            response.sendRedirect(location);
        } catch (Exception e) {
            handelError(e);
        } 
    }    


    @GET
    @Path("/sell")
    public void sellCar() {
        notify("sellCar");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            List<String> carMakes = AppContext.getInstance().getDistinctCarMake();
            request.setAttribute("carMakes", carMakes);
            request.getRequestDispatcher("/WEB-INF/sell.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    @POST
    @Path("/addSell")
    public void addSellCar() {
        notify("addSellCar");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            CarStock carStock = AppUtil.buildCarStock(request);
            System.out.println(carStock);
            carsRepo.addCarStock(carStock);
            System.out.println(carStock);
            request.setAttribute("myCarDetails", carStock);
            request.setAttribute("carMakes", AppContext.getInstance().getDistinctCarMake());
            request.setAttribute("auStates", AppContext.getInstance().getAuStates());
            request.setAttribute("modelYears", AppContext.getInstance().getModelYears());
            request.getRequestDispatcher("/WEB-INF/sellEdit.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    

    @GET
    @Path("/editSell/{carStockId}")
    public void editSellCar(@PathParam("carStockId") String carStockId) {
        notify("editSellCar");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            CarStock carStock = carsRepo.getCarStock(carStockId);
            if (!isActionAllowed(carStock.getCognitoUsername())){
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }            
            request.setAttribute("myCarDetails", carStock);
            request.setAttribute("carMakes", AppContext.getInstance().getDistinctCarMake());
            request.setAttribute("auStates", AppContext.getInstance().getAuStates());
            request.setAttribute("modelYears", AppContext.getInstance().getModelYears());
            request.getRequestDispatcher("/WEB-INF/sellEdit.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    
    @POST
    @Path("/updateSell")
    public void updateSellCar() {
        notify("updateSellCar");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            CarStock carStock = AppUtil.buildCarStock(request);

            CarStock savedCarStock = carsRepo.getCarStock(carStock.getStockId());
            
            if (!isActionAllowed(savedCarStock.getCognitoUsername())){
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }            

            carsRepo.updateCarStock(carStock);
            carStock = carsRepo.getCarStock(carStock.getStockId());
            request.setAttribute("myCarDetails", carStock);
            request.setAttribute("carMakes", AppContext.getInstance().getDistinctCarMake());
            request.setAttribute("auStates", AppContext.getInstance().getAuStates());
            request.setAttribute("modelYears", AppContext.getInstance().getModelYears());
            request.getRequestDispatcher("/WEB-INF/sellEdit.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    @POST
    @Path("/addPhoto/{carStockId}")
    public void addCarPhoto(@PathParam("carStockId") String carStockId) {
        notify("addCarPhoto");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            CarStock carStock = carsRepo.getCarStock(carStockId);
            if (!isActionAllowed(carStock.getCognitoUsername())){
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            List<byte[]> files = HttpFileUploadHandler.getFiles(request);
            carsRepo.savePhotos(files, carStockId);

            carStock = carsRepo.getCarStock(carStockId);
            System.out.println(carStock);
            request.setAttribute("myCarDetails", carStock);
            request.setAttribute("carMakes", AppContext.getInstance().getDistinctCarMake());
            request.setAttribute("auStates", AppContext.getInstance().getAuStates());
            request.setAttribute("modelYears", AppContext.getInstance().getModelYears());
            
            request.getRequestDispatcher("/WEB-INF/sellEdit.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }


    @POST
    @Path("/deletePhoto/{photoObjKey}")
    public void deleteCarPhoto(@PathParam("photoObjKey") String photoObjKey) {
        notify("deleteCarPhoto");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            Photo photo = carsRepo.getPhoto(photoObjKey);
            if(photo == null){
                return;
            }
            CarStock carStock = photo.getStockId();
            if (!isActionAllowed(carStock.getCognitoUsername())){
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            carsRepo.deletePhoto(photoObjKey);
            carStock = carsRepo.getCarStock(carStock.getStockId());
            System.out.println(carStock);
            request.setAttribute("myCarDetails", carStock);
            request.setAttribute("carMakes", AppContext.getInstance().getDistinctCarMake());
            request.setAttribute("auStates", AppContext.getInstance().getAuStates());
            request.setAttribute("modelYears", AppContext.getInstance().getModelYears());
            request.getRequestDispatcher("/WEB-INF/sellEdit.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    
    @GET
    @Path("/myCars")
    public void myCars() {
        notify("myCars");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            int pageNum = 0;
            List<CarStock> featuredCars = carsRepo.getUserCars(pageNum, getCognitoUserName());
            request.setAttribute("myCars", featuredCars);
            request.setAttribute("myCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/WEB-INF/mycars.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    
    private String getCognitoUserName(){
        return getSessionAttributeString("cognitousername");
    }

    private String getSessionAttributeString(String attributeName){
        HttpSession httpSession = request.getSession(false);
        if(httpSession != null){
            Object val = httpSession.getAttribute(attributeName);
            if(val != null && val instanceof String){
                return (String)val;
            }
        }
        return "";
    }
    
    private boolean isActionAllowed(String assetOwner){
        String loggedInUser = getCognitoUserName();
        if(assetOwner.equals(loggedInUser)){ return true;}
        return false;
    }
    
    @POST
    @Path("/prevUserCars")
    public void prevUserCars() {
        notify("prevUserCars");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            int pageNum = Integer.parseInt(request.getParameter("ucCurrPageNum") == null ? "0" : request.getParameter("ucCurrPageNum"));
            if(pageNum > 0){
                pageNum = pageNum -1;
            }
            List<CarStock> userCars = carsRepo.getUserCars(pageNum, getCognitoUserName());
            request.setAttribute("myCars", userCars);
            request.setAttribute("myCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/WEB-INF/mycars.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    @POST
    @Path("/nextUserCars")
    public void nextUserCars() {
        notify("nextUserCars");
        if(!AppUtil.isAuthenticated(request, response)) {return;}
        try {
            int pageNum = Integer.parseInt(request.getParameter("ucCurrPageNum") == null ? "0" : request.getParameter("ucCurrPageNum"));
            if(!carsRepo.isLastPageForUserCars(pageNum, getCognitoUserName())){
                pageNum = pageNum + 1;
            }
            List<CarStock> userCars = carsRepo.getUserCars(pageNum, getCognitoUserName());
            request.setAttribute("myCars", userCars);
            request.setAttribute("myCarsCurrPageNum", pageNum);
            request.getRequestDispatcher("/WEB-INF/mycars.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }

    @POST
    @Path("/deleteCar/{carStockId}")
    public void deleteCar(@PathParam("carStockId") String carStockId) {
        notify("deleteCar");
        try {
            CarStock carStock = carsRepo.getCarStock(carStockId);
            if (!isActionAllowed(carStock.getCognitoUsername())){
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
            carsRepo.deleteCarStock(carStockId);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            handelError(e);
        } 
    }
    
    public V1Resource() {
    }

    @GET
    @Path("/model")
    @Produces(MediaType.APPLICATION_JSON)
    public String getModel() {
        String make = request.getParameter("Make");
        List<Model> listModel = new ArrayList();
        Model emptyModel = new Model("","");
        listModel.add(emptyModel);
        List<Car> carTypes = AppContext.getInstance().getCarTypes();
        carTypes.stream()
                .filter(c -> c.getMake().equalsIgnoreCase(make))
                .forEach(c -> listModel.add(new Model(c.getModel(), c.getModel())));
        String json = new Gson().toJson(listModel);
        return json;
    }

    
    private void handelError(Exception exp){
        exp.printStackTrace();
        ExceptionWrapper ew = new ExceptionWrapper(exp);
        String jsonExp = new Gson().toJson(ew);
        awsSNS.send(jsonExp);
        throw new RuntimeException(exp);
    }

    private void notify(String page){
        awsSNS.send("ArifCarsWebApp " + page + " Visited by " + AppUtil.getClientIP(request) + " " + Instant.now().toString());
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
