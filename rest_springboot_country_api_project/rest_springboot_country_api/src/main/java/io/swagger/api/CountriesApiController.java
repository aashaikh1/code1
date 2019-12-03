package io.swagger.api;

import io.swagger.model.Country;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import io.swagger.dao.CountryDao;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-26T04:23:25.042Z[GMT]")
@Controller
public class CountriesApiController implements CountriesApi {
    
    private static final Logger log = LoggerFactory.getLogger(CountriesApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    
    @Autowired
    private CountryDao conntryDao;

    @org.springframework.beans.factory.annotation.Autowired
    public CountriesApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Collection<Country>> countriesGet(@Min(10) @Max(20) @ApiParam(value = "The amount of countries returned", allowableValues = "") @Valid @RequestParam(value = "bodyLimit", required = false) Integer bodyLimit,@Min(1) @Max(5) @ApiParam(value = "The pages to return countries", allowableValues = "") @Valid @RequestParam(value = "pageLimit", required = false) Integer pageLimit) {
        String accept = request.getHeader("Accept");
        return ResponseEntity.ok().body(conntryDao.getCountries());
        //return new ResponseEntity<Countries>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Country> countriesIdGet(@ApiParam(value = "The ID of the Country",required=true) @PathVariable("id") Integer id) {
        System.out.println("Country ID:" + id);
        String accept = request.getHeader("Accept");
        return ResponseEntity.ok().body(conntryDao.getCountry(id));
        //return new ResponseEntity<Country>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> countriesPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Country body) {
        System.out.println("Country:" + body);
        String accept = request.getHeader("Accept");
        conntryDao.addCountry(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
        ///return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
