package io.swagger.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Model containing country info
 */
@ApiModel(description = "Model containing country info")
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-03-26T04:23:25.042Z[GMT]")
public class Country   {
  @JsonProperty("id")
  private Integer id = null;

  @JsonProperty("country name")
  private String countryName = null;

  @JsonProperty("counntry population")
  private Integer counntryPopulation = null;

  public Country id(Integer id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(example = "5", value = "")

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Country countryName(String countryName) {
    this.countryName = countryName;
    return this;
  }

  /**
   * Get countryName
   * @return countryName
  **/
  @ApiModelProperty(example = "Australia", value = "")

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Country counntryPopulation(Integer counntryPopulation) {
    this.counntryPopulation = counntryPopulation;
    return this;
  }

  /**
   * Get counntryPopulation
   * @return counntryPopulation
  **/
  @ApiModelProperty(example = "25000000", value = "")

  public Integer getCounntryPopulation() {
    return counntryPopulation;
  }

  public void setCounntryPopulation(Integer counntryPopulation) {
    this.counntryPopulation = counntryPopulation;
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
        Objects.equals(this.counntryPopulation, country.counntryPopulation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, countryName, counntryPopulation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Country {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    countryName: ").append(toIndentedString(countryName)).append("\n");
    sb.append("    counntryPopulation: ").append(toIndentedString(counntryPopulation)).append("\n");
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
