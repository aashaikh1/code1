package dao;

import org.openapitools.model.Country;
import java.util.Collection;

public interface CountryDao {
    public Collection<Country> getCountries();
    public Country getCountry(Integer id);
    public void addCountry(Country country);
}
