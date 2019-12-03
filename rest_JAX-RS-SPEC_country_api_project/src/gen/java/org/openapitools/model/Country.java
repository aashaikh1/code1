package org.openapitools.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import javax.validation.Valid;

import io.swagger.annotations.*;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * Model containing country info
 **/
@ApiModel(description = "Model containing country info")
public class Country   {
  
  private @Valid Integer id;
  private @Valid String countryName;
  private @Valid Integer countryPopulation;

  /**
   **/
  public Country id(Integer id) {
    this.id = id;
    return this;
  }

  
  @ApiModelProperty(example = "5", value = "")
  @JsonProperty("id")
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }

  /**
   **/
  public Country countryName(String countryName) {
    this.countryName = countryName;
    return this;
  }

  
  @ApiModelProperty(example = "Australia", value = "")
  @JsonProperty("country name")
  public String getCountryName() {
    return countryName;
  }
  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  /**
   **/
  public Country countryPopulation(Integer countryPopulation) {
    this.countryPopulation = countryPopulation;
    return this;
  }

  
  @ApiModelProperty(example = "25000000", value = "")
  @JsonProperty("country population")
  public Integer getCountryPopulation() {
    return countryPopulation;
  }
  public void setCountryPopulation(Integer countryPopulation) {
    this.countryPopulation = countryPopulation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Country country = (Country) o;
    return Objects.equals(this.id, country.id) &&
        Objects.equals(this.countryName, country.countryName) &&
        Objects.equals(this.countryPopulation, country.countryPopulation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, countryName, countryPopulation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Country {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    countryName: ").append(toIndentedString(countryName)).append("\n");
    sb.append("    countryPopulation: ").append(toIndentedString(countryPopulation)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

