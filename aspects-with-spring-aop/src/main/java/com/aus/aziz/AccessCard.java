package com.aus.aziz;

import com.aus.annotations.Secret;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 *
 * @author arif
 */
@Component("accessCard")
public class AccessCard {
    private int empId = 0;
    private String type = "";
    private String building = "";
    private int floor = 0;
    private Date issueDate = null;
    private Date expiryDate = null;
    private String cardSecretCode = "";

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Secret
    public String getCardSecretCode() {
        return cardSecretCode;
    }

    public void setCardSecretCode(String cardSecretCode) {
        this.cardSecretCode = cardSecretCode;
    }
    
    
}
