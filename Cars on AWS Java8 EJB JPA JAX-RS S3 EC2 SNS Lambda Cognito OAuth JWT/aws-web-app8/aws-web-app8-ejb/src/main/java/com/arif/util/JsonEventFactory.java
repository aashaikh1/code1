package com.arif.util;

import com.arif.car.jpa.CarStock;
import static com.arif.util.Util.getCarURL;
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
public class JsonEventFactory implements EventFactory<String>{

    @Override
    public String buildEvent(CarStock carStock, EventType event) {
        TreeMap<String, String> map = new TreeMap();
        switch(event){
            case ADDCAR: 
            case UPDATECAR: {
                map.put("EVENT", event.name());
                map.put("URL", Util.getCarURL(carStock.getStockId()));
                map.put("EMAIL", carStock.getContactEmail());
            }
            break;
            case DELETECAR: {
                map.put("EVENT", event.name());
                map.put("STOCKID", carStock.getStockId());
                map.put("CAR_MAKE", carStock.getCar().getMake());
                map.put("CAR_MODEL", carStock.getCar().getModel());
                map.put("EMAIL", carStock.getContactEmail());
            }
            break;
            case ADDPHOTO:
            case DELETEPHOTO: {
                map.put("EVENT", event.name());
                map.put("URL", Util.getCarURL(carStock.getStockId()));                
                map.put("STOCKID", carStock.getStockId());
                map.put("CAR_MAKE", carStock.getCar().getMake());
                map.put("CAR_MODEL", carStock.getCar().getModel());
                map.put("EMAIL", carStock.getContactEmail());
            }
            break;
        }
        map.put("TIMESTAMP", Instant.now().toString());
        return getJSONString(map);
    }
    
    
    private String getJSONString(Map map){
        if(map == null || map.size() < 1){return "";}
        Gson gson = new Gson();
        Type mapType = new TypeToken<HashMap<String, String>>() {}.getType();
        return gson.toJson(map, mapType);
        //Gson gson = new GsonBuilder().serializeNulls().create();
        //gson.toJson(test, mapType);        
    }
}
