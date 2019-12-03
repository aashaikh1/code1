package io.swagger.dao;

import io.swagger.model.Country;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class CountryDaoImpl implements CountryDao {

    private HashMap<Integer, Country> countries = new HashMap<Integer, Country>();

    public CountryDaoImpl(){
        Country australia = new Country();
        australia.setId(10);
        australia.setCountryName("Australia");
        australia.setCounntryPopulation(25000000);
        countries.put(10, australia);

        Country iceland = new Country();
        iceland.setId(11);
        iceland.setCountryName("Iceland");
        iceland.setCounntryPopulation(360000);
        countries.put(11, iceland);
    }
    
    @Override
    public Collection<Country> getCountries() {
        Collection<Country> countryList = this.countries.<Country>values();
        return Collections.unmodifiableCollection(countryList) ;
    }

    @Override
    public Country getCountry(Integer id) {
        return countries.get(id);
    }

    @Override
    public void addCountry(Country country) {
        countries.put(country.getId(), country);
    }
    
}
