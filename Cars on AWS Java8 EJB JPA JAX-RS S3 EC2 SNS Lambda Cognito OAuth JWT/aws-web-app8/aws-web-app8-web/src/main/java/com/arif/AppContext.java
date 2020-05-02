package com.arif;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.InitialContext;
import com.arif.car.jpa.Car;
import java.util.Arrays;
import javax.naming.Context;
import java.util.stream.Collectors;
/**
 *
 * @author arif
 */
public class AppContext {

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private String LOCALHOST;
    private String COGNITO_DOMAIN;
    private String COGNITO_DOMAIN_LOGIN;
    private String COGNITO_DOMAIN_LOGOUT;
    private String CALLBACK_URL;
    private String AWS_REGION;
    private String POOL_ID;
    private String JWKS_URL;
    private String COGNITO_TRIPBACK_URL;
    private String HOME_URL;
    private String LOGOUT_URL;

    private static AppContext appContext;
    private static String propFile = "app.properties";
    private List<Car> carTypes = new ArrayList();
    private List<String> distinctCarMake = new ArrayList();
    private List<String> auStates = Arrays.asList("ACT", "NSW", "QLD", "VIC", "TAS", "SA", "WA", "NT");
    private List<String> modelYears = Arrays.asList("2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013", "2012", "2011"
                                                        , "2010", "2009", "2008", "2007", "2006", "2005", "2004", "2003", "2002", "2001");

    public static AppContext getInstance() {
        if (appContext == null) {
            synchronized (AppContext.class) {
                if (appContext == null) {
                    appContext = new AppContext();
                }
            }
        }
        return appContext;
    }

    private AppContext() {
        try (InputStream input = AppContext.class.getResourceAsStream("/app.properties");) {
            Properties prop = new Properties();
            prop.load(input);
            CLIENT_ID = prop.getProperty("CLIENT_ID");
            CLIENT_SECRET = prop.getProperty("CLIENT_SECRET");
            LOCALHOST = prop.getProperty("LOCALHOST");
            COGNITO_DOMAIN = prop.getProperty("COGNITO_DOMAIN");
            COGNITO_DOMAIN_LOGIN = prop.getProperty("COGNITO_DOMAIN_LOGIN");
            CALLBACK_URL = prop.getProperty("CALLBACK_URL");
            AWS_REGION = prop.getProperty("AWS_REGION");
            POOL_ID = prop.getProperty("POOL_ID");
            JWKS_URL = prop.getProperty("JWKS_URL");
            COGNITO_TRIPBACK_URL = prop.getProperty("COGNITO_TRIPBACK_URL");
            HOME_URL = prop.getProperty("HOME_URL");
            COGNITO_DOMAIN_LOGOUT = prop.getProperty("COGNITO_DOMAIN_LOGOUT");
            LOGOUT_URL = prop.getProperty("LOGOUT_URL");

            loadCarTypes();
        } catch (IOException ex) {
            throw new RuntimeException("Unable to load properties from: " + propFile, ex);
        }
    }

    public String getCLIENT_ID() {
        return CLIENT_ID;
    }

    public String getCLIENT_SECRET() {
        return CLIENT_SECRET;
    }

    public String getLOCALHOST() {
        return LOCALHOST;
    }

    public String getCOGNITO_DOMAIN() {
        return COGNITO_DOMAIN;
    }

    public String getAWS_REGION() {
        return AWS_REGION;
    }

    public String getPOOL_ID() {
        return POOL_ID;
    }

    public String getJWKS_URL() {
        return JWKS_URL;
    }

    public String getCOGNITO_DOMAIN_LOGIN() {
        return COGNITO_DOMAIN_LOGIN;
    }

    public String getCALLBACK_URL() {
        return CALLBACK_URL;
    }

    public String getCOGNITO_TRIPBACK_URL() {
        return COGNITO_TRIPBACK_URL;
    }

    public String getHOME_URL() {
        return HOME_URL;
    }

    public String getCOGNITO_DOMAIN_LOGOUT() {
        return COGNITO_DOMAIN_LOGOUT;
    }

    public String getLOGOUT_URL() {
        return LOGOUT_URL;
    }

    /*
    * Car Types are static data and I have not given ability to add new car types form frontend
    * only I can add new carr types directly into database from backend
    * so loading car types once at application startup is ok here.
    */
    private void loadCarTypes() {
        Context context = null;
        try {
            context = new InitialContext();
            CarsRepo carsRepo = (CarsRepo) context.lookup("java:app/com.arif-aws-web-app8-ejb-1.0-SNAPSHOT/EJBCarsRepo!com.arif.CarsRepo");
            carTypes = carsRepo.getCarTypes();

            distinctCarMake = carTypes.stream()
                .map(c -> c.getMake())
                .distinct()
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Problem in AppContext.loadCarTypes", e);
        }
    }

    public List<Car> getCarTypes() {
        return carTypes;
    }
    
    public List<String> getDistinctCarMake() {
        return distinctCarMake;
    }

    public List<String> getAuStates() {
        return auStates;
    }

    public List<String> getModelYears() {
        return modelYears;
    }
    

}
