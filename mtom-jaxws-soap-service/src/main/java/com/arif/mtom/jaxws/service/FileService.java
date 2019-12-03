package com.arif.mtom.jaxws.service;

import java.io.FileOutputStream;
import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.ejb.Stateless;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.soap.MTOM;

/**
 *
 * @author arif
 */
@MTOM(enabled=true, threshold=0)
@WebService(serviceName = "FileService")
@Stateless()
public class FileService {

    @WebMethod(operationName = "upload")
    public void upload(@WebParam(name = "fileName") String fileName,
            @XmlMimeType("application/octat-stream") @WebParam(name = "data") DataHandler data) {
        try{
            FileOutputStream os=new FileOutputStream(fileName);
            data.writeTo(os);
            os.close();        
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(String[] args){
        String url = "http://localhost:9721/FileService";
        System.out.println("FileService listening at: " + url);
        System.out.println("FileService generated wsdl at: " + url + "?wsdl");
        Endpoint.publish(url, new FileService());
    }
}
