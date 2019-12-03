package com.aus.arif;

import com.aus.annotations.Secret;
import org.springframework.stereotype.Component;


/**
 *
 * @author arif
 */
@Component("emp")
public class Employee {
    private String name = "";
    private String departmentName = "";
    private int empId = 0;
    private double salary = 0.0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    @Secret
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
}
