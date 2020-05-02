package com.arif;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 *
 * @author arif
 */
public class CarAppContext {

    private String NOTIFICATION_TOPIC;
    private String EVENT_TOPIC;
    private String CARSTOCK_URL;

    private static CarAppContext appContext;
    private static String propFile = "app.properties";

    public static CarAppContext getInstance() {
        if (appContext == null) {
            synchronized (CarAppContext.class) {
                if (appContext == null) {
                    appContext = new CarAppContext();
                }
            }
        }
        return appContext;
    }

    private CarAppContext() {
        try (InputStream input = CarAppContext.class.getResourceAsStream("/cars.properties");) {
            Properties prop = new Properties();
            prop.load(input);
            NOTIFICATION_TOPIC = prop.getProperty("NOTIFICATION_TOPIC");
            EVENT_TOPIC = prop.getProperty("EVENT_TOPIC");
            CARSTOCK_URL = prop.getProperty("CARSTOCK_URL");
        } catch (IOException ex) {
            throw new RuntimeException("Unable to load properties from: " + propFile, ex);
        }
    }

    public String getNOTIFICATION_TOPIC() {
        return NOTIFICATION_TOPIC;
    }

    public String getEVENT_TOPIC() {
        return EVENT_TOPIC;
    }

    public String getCARSTOCK_URL() {
        return CARSTOCK_URL;
    }

}
