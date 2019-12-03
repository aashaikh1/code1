package org.openapitools.api;

import dao.CountryDao;
import dao.CountryDaoImpl;
import org.openapitools.model.Country;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.annotations.*;

import javax.validation.constraints.*;
import javax.validation.Valid;

@Path("/countries")
@Api(description = "the countries API")
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaJAXRSSpecServerCodegen", date = "2019-11-10T21:56:07.245+11:00[Australia/Sydney]")
public class CountriesApi {

    private CountryDao countryDao;
    
    public CountriesApi(){
        countryDao = getCountryDao();
    }
    
    private CountryDao getCountryDao(){
        return CountryDaoImpl.getInstance();
    }
    
    @GET
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Get information about Coutries from the database", response = Country.class, responseContainer = "List", tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful pull of country info", response = Country.class, responseContainer = "List")
    })
    public Response countriesGet(@QueryParam("bodyLimit") @Min(10) @Max(20)   @ApiParam("The amount of countries returned")  Integer bodyLimit,@QueryParam("pageLimit") @Min(1) @Max(5)   @ApiParam("The pages to return countries")  Integer pageLimit) {
        return Response.ok().entity(countryDao.getCountries()).build();
    }

    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    @ApiOperation(value = "", notes = "Obtain information about specific Country", response = Country.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Success", response = Country.class)
    })
    public Response countriesIdGet(@PathParam("id") @ApiParam("The ID of the Country") Integer id) {
        return Response.ok().entity(countryDao.getCountry(id)).build();
    }

    @POST
    @Consumes({ "application/json" })
    @ApiOperation(value = "", notes = "Creates a new Country in the database", response = Void.class, tags={  })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successfully created a new Country", response = Void.class)
    })
    public Response countriesPost(@Valid Country country) {
        System.out.println(country);
        countryDao.addCountry(country);
        return Response.ok().build();
    }
}
