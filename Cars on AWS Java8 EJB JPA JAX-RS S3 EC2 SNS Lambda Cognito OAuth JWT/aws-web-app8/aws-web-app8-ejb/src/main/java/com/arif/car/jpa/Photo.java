package com.arif.car.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author arif
 */
@Entity
@Table(name = "photo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Photo.findAll", query = "SELECT p FROM Photo p"),
    @NamedQuery(name = "Photo.findByObjectKey", query = "SELECT p FROM Photo p WHERE p.objectKey = :objectKey"),
    @NamedQuery(name = "Photo.findByLabels", query = "SELECT p FROM Photo p WHERE p.labels = :labels"),
    @NamedQuery(name = "Photo.findByDescription", query = "SELECT p FROM Photo p WHERE p.description = :description"),
    @NamedQuery(name = "Photo.findByCreatedDatetime", query = "SELECT p FROM Photo p WHERE p.createdDatetime = :createdDatetime"),
    @NamedQuery(name = "Photo.findByDeleted", query = "SELECT p FROM Photo p WHERE p.deleted = :deleted"),
    @NamedQuery(name = "Photo.findByVersion", query = "SELECT p FROM Photo p WHERE p.version = :version")})
public class Photo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "object_key")
    private String objectKey; //String uniqueID = UUID.randomUUID().toString();
    
    @Size(max = 200)
    @Column(name = "labels")
    private String labels; 
    
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    
    @Column(name = "created_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDatetime;
    
    @Size(max = 3)
    @Column(name = "deleted")
    private String deleted;
    
    @Version
    private Integer version;
    
    @JoinColumn(name = "stock_id", referencedColumnName = "stock_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private CarStock stockId;
    
    @Transient
    private String awsPreSignedUrl = "";

    public Photo() {
    }

    public Photo(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getObjectKey() {
        return objectKey;
    }

    public void setObjectKey(String objectKey) {
        this.objectKey = objectKey;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public CarStock getStockId() {
        return stockId;
    }

    public void setStockId(CarStock stockId) {
        this.stockId = stockId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (objectKey != null ? objectKey.hashCode() : 0);
        return hash;
    }

    public String getAwsPreSignedUrl() {
        return awsPreSignedUrl;
    }

    public void setAwsPreSignedUrl(String _awsPreSignedUrl) {
        this.awsPreSignedUrl = _awsPreSignedUrl;
    }
    
    @Override
    public boolean equals(Object object) {
        // I am not using JPA generated IDs using @GeneratedValue rather I am assigning IDs using UUID.
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.objectKey == null && other.objectKey != null) || (this.objectKey != null && !this.objectKey.equals(other.objectKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.arif.car.jpa.Photo[ objectKey=" + objectKey + " ]";
    }
    
}
