package com.arif.car.jpa;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arif
 */
@Entity
@Table(name = "car")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Car.findAll", query = "SELECT c FROM Car c"),
    @NamedQuery(name = "Car.findByCarId", query = "SELECT c FROM Car c WHERE c.carId = :carId"),
    @NamedQuery(name = "Car.findByMake", query = "SELECT c FROM Car c WHERE c.make = :make"),
    @NamedQuery(name = "Car.findByModel", query = "SELECT c FROM Car c WHERE c.model = :model"),
    @NamedQuery(name = "Car.findByMakeAndModel", query = "SELECT c FROM Car c WHERE c.make = :make and c.model = :model"),
    @NamedQuery(name = "Car.findByYear", query = "SELECT c FROM Car c WHERE c.year = :year"),
    @NamedQuery(name = "Car.findByBodytype", query = "SELECT c FROM Car c WHERE c.bodytype = :bodytype"),
    @NamedQuery(name = "Car.findByKeywords", query = "SELECT c FROM Car c WHERE c.keywords = :keywords"),
    @NamedQuery(name = "Car.findByDescription", query = "SELECT c FROM Car c WHERE c.description = :description"),
    @NamedQuery(name = "Car.findByInternaluse", query = "SELECT c FROM Car c WHERE c.internaluse = :internaluse"),
    @NamedQuery(name = "Car.findByDeleted", query = "SELECT c FROM Car c WHERE c.deleted = :deleted"),
    @NamedQuery(name = "Car.findByVersion", query = "SELECT c FROM Car c WHERE c.version = :version")})
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "car_id")
    private Integer carId;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "make")
    private String make;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "model")
    private String model;
    
    @Column(name = "year")
    private Integer year;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "bodytype")
    private String bodytype;
    
    @Size(max = 200)
    @Column(name = "keywords")
    private String keywords;
    
    @Size(max = 1000)
    @Column(name = "description")
    private String description;
    
    @Size(max = 200)
    @Column(name = "internaluse")
    private String internaluse;
    
    @Size(max = 3)
    @Column(name = "deleted")
    private String deleted;
    
    @Version
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "car", orphanRemoval = false)
    private Set<CarStock> carStocks = new HashSet();

    public Car() {
    }

    public Car(Integer carId) {
        this.carId = carId;
    }

    public Car(Integer carId, String make, String model, String bodytype) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.bodytype = bodytype;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getBodytype() {
        return bodytype;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInternaluse() {
        return internaluse;
    }

    public void setInternaluse(String internaluse) {
        this.internaluse = internaluse;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Set<CarStock> getCarStocks() {
        return carStocks;
    }

    public void setCarStocks(Set<CarStock> carStocks) {
        this.carStocks = carStocks;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carId != null ? carId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // I am not using JPA generated IDs using @GeneratedValue rather I am assigning IDs using UUID.
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.carId == null && other.carId != null) || (this.carId != null && !this.carId.equals(other.carId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Car{" + "carId=" + carId + ", make=" + make + ", model=" + model + ", year=" + year + ", bodytype=" + bodytype + ", keywords=" + keywords + ", description=" + description + ", internaluse=" + internaluse + ", deleted=" + deleted + ", version=" + version + '}';
    }

}
