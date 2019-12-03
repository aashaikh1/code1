package dao;

import org.openapitools.model.Country;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

//A simple DAO. Its Singleton cause if REST service is accessed from muultiple clients and
//multiple instaces of REST service are created then we need to avoid multiple instances of this simple datastore
//this DAO could be easily replaced with JPA based data access without sngleton but emphasis of this project is REST service (not JPA).
public class CountryDaoImpl implements CountryDao {

    private HashMap<Integer, Country> countries = new HashMap<Integer, Country>();
    
    private static CountryDao countryDao;

    public static CountryDao getInstance(){
        if(countryDao == null){
            synchronized(CountryDaoImpl.class){
                if(countryDao == null){
                    countryDao = new CountryDaoImpl();
                }
            }
        }
        return countryDao;
    } 
    
    private CountryDaoImpl(){
        Country australia = new Country();
        australia.setId(10);
        australia.setCountryName("Australia");
        australia.setCountryPopulation(25000000);
        countries.put(10, australia);

        Country iceland = new Country();
        iceland.setId(11);
        iceland.setCountryName("Iceland");
        iceland.setCountryPopulation(360000);
        countries.put(11, iceland);
    }
    
    @Override
    public synchronized Collection<Country> getCountries() {
        System.out.println(countries);
        Collection<Country> countryList = this.countries.<Country>values();
        return Collections.unmodifiableCollection(countryList) ;
    }

    @Override
    public synchronized Country getCountry(Integer id) {
        return countries.get(id);
    }

    @Override
    public synchronized void addCountry(Country country) {
        countries.put(country.getId(), country);
        System.out.println(countries);
    }
    
}
