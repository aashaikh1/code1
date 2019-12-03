package com.aus;

import com.aus.arif.Employee;
import com.aus.aziz.AccessCard;
import java.util.Date;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author arif
 */
public class ContextLoader {
    public static void main(String[] args){
        ApplicationContext ctx =  new ClassPathXmlApplicationContext("classpath*:*-config.xml");
        //ApplicationContext ctx =  new ClassPathXmlApplicationContext(new String[] {"aspects-config.xml", "application-config.xml"});
        Employee emp = ctx.getBean("emp", Employee.class);
        AccessCard accessCard = ctx.getBean("accessCard", AccessCard.class);
        
        int empId = 1000071;
        emp.setDepartmentName("IT");
        emp.setEmpId(empId);
        emp.setName("Abraham");
        emp.setSalary(200000000);
        
        
        accessCard.setEmpId(empId);
        accessCard.setBuilding("Nuilding No 7");
        accessCard.setCardSecretCode("hiddenCode");
        accessCard.setIssueDate(new Date());
        accessCard.setExpiryDate(new Date());
        accessCard.setType("temporary");

        //below get method invocation will trigger the LoggingAspect advice on Employee.get methods without @Secret as per the pointcut given in @Around
        String empInfo = emp.getName() + " " + emp.getEmpId() + " " + emp.getDepartmentName() + " " + emp.getSalary();
        
        String accessCardInfo = accessCard.getEmpId() + " " + accessCard.getType()  + " " +  accessCard.getBuilding()  
                + " " + accessCard.getCardSecretCode()  + " " + accessCard.getIssueDate()  + " " + accessCard.getExpiryDate()
                 + " " + accessCard.getFloor();
    }
}
