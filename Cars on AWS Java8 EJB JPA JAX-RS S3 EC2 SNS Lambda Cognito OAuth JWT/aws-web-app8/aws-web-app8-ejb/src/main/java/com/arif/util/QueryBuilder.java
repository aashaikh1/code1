package com.arif.util;

import com.arif.CarSearchCriteria;
import com.arif.util.Util;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author arif
 */
public class QueryBuilder {
    public static Statement buildSearchCarsQuery(CarSearchCriteria criteria){
        if(criteria == null){
            return null;
        }
        
        String query = "SELECT s FROM CarStock s WHERE ";
        List<String> params = new ArrayList();
        
        if(Util.isNotEmpty(criteria.getMake())){
            query = query + "s.car.make = :make and ";
            params.add("make");
            
            if(Util.isNotEmpty(criteria.getModel())){
                query = query + "s.car.model = :model and ";
                params.add("model");
            }
        }
        
        if(Util.isNotEmpty(criteria.getLocation())){
            query = query + "s.location = :location and ";
            params.add("location");
        }
        
        if(criteria.getPriceMin() > 0){
            query = query + "s.price >= :priceMin and ";
            params.add("priceMin");
        }
        
        if(criteria.getPriceMax() > 0){
            query = query + "s.price <= :priceMax and ";
            params.add("priceMax");
        }

        if(criteria.getModelYear() > 0){
            query = query + "s.year = :modelYear and ";
            params.add("modelYear");
        }
        
        if(Util.isNotEmpty(criteria.getNewCar()) && !criteria.getNewCar().equalsIgnoreCase("any")){
            query = query + "s.usage = :usage and";
            params.add("usage");
        }
        
        System.out.println("query: " + query + " size:" + query.length() + " lastIndexOf: " + query.lastIndexOf("and"));
        
        if(query.lastIndexOf("and") < 0){
            query = query.replace("WHERE", "");
        }else{
            query = query.substring(0, query.lastIndexOf("and"));
        }
        
        query = query + " order by s.stockId";
        System.out.println("query: " + query);
        Statement stmt = new Statement(query, params);
        
        return stmt;
    }
    
    public static Statement buildCountSearchCarsQuery(CarSearchCriteria criteria){
        Statement statement = buildSearchCarsQuery(criteria);
        statement.query = statement.query.replace("SELECT s", "SELECT COUNT(s)");
        return statement;
    }
    
    public static void setStockCarQueryParameters(TypedQuery carStockQuery, QueryBuilder.Statement stmt, CarSearchCriteria searchCriteria){
        for(String param:stmt.getParams()){
            switch(param){
                case "make": carStockQuery.setParameter(param, searchCriteria.getMake()); break;
                case "model": carStockQuery.setParameter(param, searchCriteria.getModel()); break;
                case "location": carStockQuery.setParameter(param, searchCriteria.getLocation()); break;
                case "priceMin": carStockQuery.setParameter(param, searchCriteria.getPriceMin()); break;
                case "priceMax": carStockQuery.setParameter(param, searchCriteria.getPriceMax()); break;
                case "usage": carStockQuery.setParameter(param, searchCriteria.getNewCar()); break;
                case "modelYear": carStockQuery.setParameter(param, searchCriteria.getModelYear()); break;
            }
        }        
    }    
    
    public static class Statement{
        private String query = "";
        private List<String> params = new ArrayList();
        public Statement(String _query, List<String> _params){
            this.query = _query;
            this.params = _params;
        }

        public String getQuery() {
            return query;
        }

        public List<String> getParams() {
            return params;
        }
        
    }
    
}
