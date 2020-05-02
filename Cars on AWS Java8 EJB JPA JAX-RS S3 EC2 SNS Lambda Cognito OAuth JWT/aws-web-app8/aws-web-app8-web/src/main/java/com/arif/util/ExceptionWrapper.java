package com.arif.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;

/**
 *
 * @author arif
 */
public class ExceptionWrapper {
    private String time;    
    private Exception exp;
    private String appName = "ArifCarsWebApp";
    private String exceptionDetails = null;

    
    public ExceptionWrapper(Exception e){
        this.exp = e;
        this.time = Instant.now().toString();
    }
    
    public ExceptionWrapper(Exception e, String _exceptionDetails){
        this.exp = e;
        this.exceptionDetails = _exceptionDetails;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExp() {
        return exp.getMessage();
    }

    public void setExp(Exception exp) {
        this.exp = exp;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getExceptionDetails() {
        if(exceptionDetails != null){
            return exceptionDetails;
        }
        if(this.exp != null){
            return this.exp.getMessage();
        }
        return "EXCEPTION OBJECT IS NULL";
    }

    public void setExceptionDetails(String exceptionDetails) {
        this.exceptionDetails = exceptionDetails;
    }

    public static String getStackTraceString(Exception ex){
        if(ex == null) {return "";}
        StringWriter errors = new StringWriter();
        ex.printStackTrace(new PrintWriter(errors));
        return errors.toString();        
    }
    
}
