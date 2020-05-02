package com.arif.util;
import com.arif.CarAppContext;
import com.arif.car.jpa.CarStock;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author arif
 */
public class Util {
    public static boolean isEmpty(String str){
        if(str == null || str.trim().isEmpty()){
            return true;
        }
        return false;
    }
    
    public static boolean isNotEmpty(String str){
        return !Util.isEmpty(str);
    }
    
    public static void copyBasicValues(CarStock source, CarStock target){
        target.setContactPhone(source.getContactPhone());
        target.setDescription(source.getDescription());
        target.setKeywords(source.getKeywords());
        target.setKilometers(source.getKilometers());
        target.setLocation(source.getLocation());
        target.setPrice(source.getPrice());
        target.setUsage(source.getUsage());
        target.setYear(source.getYear());
        target.setVersion(source.getVersion());
    }
    
    public static String getCarURL(String stockId){
        return CarAppContext.getInstance().getCARSTOCK_URL() + stockId;
    }    
}
