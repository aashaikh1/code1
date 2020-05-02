package com.arif;

/**
 *
 * @author arif
 */
public class CarSearchCriteria {
    private String make;
    private String model;
    private String location;
    private int priceMin = -1;
    private int priceMax = -1;
    private String newCar;
    private String keywords;
    private String description;
    private String phone;
    private String email;
    private String cognitoUsername;
    private int modelYear = -1;
    
    

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPriceMin() {
        return priceMin;
    }

    public void setPriceMin(String _priceMin) {
        if(_priceMin != null && !_priceMin.trim().isEmpty()){
            this.priceMin = Integer.parseInt(_priceMin);
        }else{
            this.priceMin = 0;
        }
        
    }

    public int getPriceMax() {
        return priceMax;
    }

    public void setPriceMax(String _priceMax) {
        if(_priceMax != null && !_priceMax.trim().isEmpty()){
            this.priceMax = Integer.parseInt(_priceMax);
        }else{
            this.priceMax = 0;
        }
    }

    public String getNewCar() {
        return newCar;
    }

    public void setNewCar(String newCar) {
        this.newCar = newCar;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String _description) {
        this.description = _description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCognitoUsername() {
        return cognitoUsername;
    }

    public void setCognitoUsername(String cognitoUsername) {
        this.cognitoUsername = cognitoUsername;
    }

    public int getModelYear() {
        return modelYear;
    }

    public void setModelYear(String _modelYear) {
        if(_modelYear != null && !_modelYear.trim().isEmpty()){
            this.modelYear = Integer.parseInt(_modelYear);
        }else{
            this.modelYear = 0;
        }        
    }

    @Override
    public String toString() {
        return "CarSearchCriteria{" + "make=" + make + ", model=" + model + ", location=" + location + ", priceMin=" + priceMin + ", priceMax=" + priceMax + ", newCar=" + newCar + ", keywords=" + keywords + ", description=" + description + ", phone=" + phone + ", email=" + email + ", cognitoUsername=" + cognitoUsername + ", modelYear=" + modelYear + '}';
    }

    

    
    
}
