/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arif;

import com.arif.util.QueryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author arif
 */
public class QueryBuilderTest {
    
    public QueryBuilderTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }
    


    /**
     * Test of buildSearchCarsQuery method, of class QueryBuilder.
     */
    @org.junit.jupiter.api.Test
    public void testBuildSearchCarsQuery() {
        System.out.println("buildSearchCarsQuery");
        CarSearchCriteria criteria = new CarSearchCriteria();
        criteria.setMake("Toyota");
        criteria.setModel("86");
        criteria.setLocation("ACT");
        criteria.setPriceMin("20000");
        criteria.setPriceMax("60000");
        criteria.setNewCar("Any");
        
        String expResult = "SELECT s FROM CarStock s WHERE s.car.make = :make and s.car.model = :model and s.location = :location and s.price >= :priceMin and s.price <= :priceMax  order by s.stockId";
        QueryBuilder.Statement result = QueryBuilder.buildSearchCarsQuery(criteria);
        assertEquals(expResult, result.getQuery());
    }
    
}
