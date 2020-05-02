package com.arif.car.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arif
 */
@Entity
@Table(name = "car_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CarStock.findAll", query = "SELECT c FROM CarStock c"),
    @NamedQuery(name = "CarStock.findByStockId", query = "SELECT c FROM CarStock c WHERE c.stockId = :stockId"),
    @NamedQuery(name = "CarStock.findByYear", query = "SELECT c FROM CarStock c WHERE c.year = :year"),
    @NamedQuery(name = "CarStock.findByPrice", query = "SELECT c FROM CarStock c WHERE c.price = :price"),
    @NamedQuery(name = "CarStock.findByLocation", query = "SELECT c FROM CarStock c WHERE c.location = :location"),
    @NamedQuery(name = "CarStock.findByContactPhone", query = "SELECT c FROM CarStock c WHERE c.contactPhone = :contactPhone"),
    @NamedQuery(name = "CarStock.findByContactEmail", query = "SELECT c FROM CarStock c WHERE c.contactEmail = :contactEmail"),
    @NamedQuery(name = "CarStock.findByKeywords", query = "SELECT c FROM CarStock c WHERE c.keywords = :keywords"),
    @NamedQuery(name = "CarStock.findByDescription", query = "SELECT c FROM CarStock c WHERE c.description = :description"),
    @NamedQuery(name = "CarStock.findByInternaluse", query = "SELECT c FROM CarStock c WHERE c.internaluse = :internaluse"),
    @NamedQuery(name = "CarStock.findByCognitoUsernameAll", query = "SELECT c FROM CarStock c WHERE c.cognitoUsername = :cognitoUsername"),
    @NamedQuery(name = "CarStock.findByCognitoUsername", query = "SELECT c FROM CarStock c WHERE c.cognitoUsername = :cognitoUsername and c.deleted = null order by c.createdDatetime"),
    @NamedQuery(name = "CarStock.countByCognitoUsername", query = "SELECT count(c) FROM CarStock c WHERE c.cognitoUsername = :cognitoUsername and c.deleted = null"),
    @NamedQuery(name = "CarStock.findByCreatedDatetime", query = "SELECT c FROM CarStock c WHERE c.createdDatetime = :createdDatetime"),
    @NamedQuery(name = "CarStock.findByFeaturerd", query = "SELECT c FROM CarStock c WHERE c.internaluse like '%FEATURED%' order by c.createdDatetime"),
    @NamedQuery(name = "CarStock.countByFeaturerd", query = "SELECT count(c) FROM CarStock c WHERE c.internaluse like '%FEATURED%'"),
    @NamedQuery(name = "CarStock.findByDeleted", query = "SELECT c FROM CarStock c WHERE c.deleted = :deleted"),
    @NamedQuery(name = "CarStock.findByVersion", query = "SELECT c FROM CarStock c WHERE c.version = :version")})
public class CarStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "stock_id")
    private String stockId;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "year")
    private int year;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private int price;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "location")
    private String location;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "contact_phone")
    private String contactPhone;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "contact_email")
    private String contactEmail;
    
    @Size(max = 200)
    @Column(name = "keywords")
    private String keywords;
    
    @Size(max = 1000)
    @Column(name = "description")
    private String description;
    
    @Size(max = 200)
    @Column(name = "internaluse")
    private String internaluse;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "cognito_username")
    private String cognitoUsername;
    
    @Column(name = "created_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDatetime;
    
    @Size(max = 3)
    @Column(name = "deleted")
    private String deleted;

    @Size(max = 3)
    @Column(name = "new")
    private String usage;

    @Basic(optional = false)
    @Column(name = "kilometers")
    private int kilometers;
    
    @Version
    private Integer version;

    @OneToMany(cascade = CascadeType.ALL , fetch = FetchType.EAGER , mappedBy = "stockId", orphanRemoval = false) //cascade todo optimistic things no delete 
    private Set<Photo> photos = new HashSet();

    @JoinColumn(name = "car_id", referencedColumnName = "car_id")
    @ManyToOne(fetch = FetchType.EAGER , optional = false)
    private Car car;

    public CarStock() {
    }

    public CarStock(String stockId) {
        this.stockId = stockId;
    }

    public CarStock(String stockId, int year, int price, String location, String contactPhone, String contactEmail, String cognitoUsername) {
        this.stockId = stockId;
        this.year = year;
        this.price = price;
        this.location = location;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.cognitoUsername = cognitoUsername;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getDescription() {
        return description.trim();
    }

    public void setDescription(String _description) {
        this.description = _description.trim();
    }

    public String getInternaluse() {
        return internaluse;
    }

    public void setInternaluse(String internaluse) {
        this.internaluse = internaluse;
    }

    public String getCognitoUsername() {
        return cognitoUsername;
    }

    public void setCognitoUsername(String cognitoUsername) {
        this.cognitoUsername = cognitoUsername;
    }

    public Date getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(Date createdDatetime) {
        this.createdDatetime = createdDatetime;
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

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }



    public Car getCar() {
        return car;
    }

    public void setCar(Car _car) {
        this.car = _car;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stockId != null ? stockId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // I am not using JPA generated IDs using @GeneratedValue rather I am assigning IDs using UUID.
        if (!(object instanceof CarStock)) {
            return false;
        }
        CarStock other = (CarStock) object;
        if ((this.stockId == null && other.stockId != null) || (this.stockId != null && !this.stockId.equals(other.stockId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarStock{" + "stockId=" + stockId + ", year=" + year + ", price=" + price + ", location=" + location + ", contactPhone=" + contactPhone + ", contactEmail=" + contactEmail + ", keywords=" + keywords + ", description=" + description + ", internaluse=" + internaluse + ", cognitoUsername=" + cognitoUsername + ", createdDatetime=" + createdDatetime + ", deleted=" + deleted + ", usage=" + usage + ", kilometers=" + kilometers + ", version=" + version + ", car=" + car + '}';
    }





}
