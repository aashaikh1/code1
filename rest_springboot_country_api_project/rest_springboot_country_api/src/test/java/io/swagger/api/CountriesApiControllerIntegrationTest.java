package io.swagger.api;

import io.swagger.model.Country;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountriesApiControllerIntegrationTest {

    @Autowired
    private CountriesApi api;

    @Test
    public void countriesGetTest() throws Exception {
        Integer bodyLimit = 56;
        Integer pageLimit = 56;
        ResponseEntity<Collection<Country>> responseEntity = api.countriesGet(bodyLimit, pageLimit);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void countriesIdGetTest() throws Exception {
        Integer id = 11;
        ResponseEntity<Country> responseEntity = api.countriesIdGet(id);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void countriesPostTest() throws Exception {
        Country body = new Country();
        body.setId(-1);
        body.setCountryName("England");
        ResponseEntity<Void> responseEntity = api.countriesPost(body);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
