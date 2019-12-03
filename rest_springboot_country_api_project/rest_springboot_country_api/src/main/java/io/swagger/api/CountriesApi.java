package io.swagger.api;

import io.swagger.model.Country;
import io.swagger.annotations.*;
import java.util.Collection;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-26T04:23:25.042Z[GMT]")
@Api(value = "countries", description = "the countries API")
public interface CountriesApi {

    @ApiOperation(value = "", nickname = "countriesGet", notes = "Get information about Coutries from the database", response = Collection.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful pull of country info", response = Collection.class) })
    @RequestMapping(value = "/countries",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Collection<Country>> countriesGet(@Min(10) @Max(20) @ApiParam(value = "The amount of countries returned", allowableValues = "") @Valid @RequestParam(value = "bodyLimit", required = false) Integer bodyLimit,@Min(1) @Max(5) @ApiParam(value = "The pages to return countries", allowableValues = "") @Valid @RequestParam(value = "pageLimit", required = false) Integer pageLimit);


    @ApiOperation(value = "", nickname = "countriesIdGet", notes = "Obtain information about specific Country", response = Country.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Country.class) })
    @RequestMapping(value = "/countries/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Country> countriesIdGet(@ApiParam(value = "The ID of the Country",required=true) @PathVariable("id") Integer id);


    @ApiOperation(value = "", nickname = "countriesPost", notes = "Creates a new Country in the database", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully created a new Country") })
    @RequestMapping(value = "/countries",
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> countriesPost(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Country body);

}
