package com.arif;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.*;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author arif
 */
public class HttpFileUploadHandler {

    public static List<byte[]> getFiles(HttpServletRequest request) {
        boolean isMultipartRequest = ServletFileUpload.isMultipartContent(request);
        List<byte[]> uploadedFiles = new ArrayList();
        if (isMultipartRequest) {
            FileItemFactory fileItemFactory = new DiskFileItemFactory();
            List<FileItem> itemList = null;
            ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
            try {
                itemList = upload.parseRequest(request);
            } catch (Exception e) {
                throw new RuntimeException("Problem in HttpFileUploadHandler.getFiles", e);
            }
            
            for(FileItem item:itemList){
                if(!item.isFormField()){
                    try{
                        byte[] fileBytes = IOUtils.toByteArray(item.getInputStream());
                        if(fileBytes.length > 0 && fileBytes.length <= 512000){
                            uploadedFiles.add(fileBytes);
                        }
                    }catch(Exception e){
                        throw new RuntimeException("Exception processing uploaded fileItem named: " + item.getName(), e);
                    }
                } 
            }
        }
        return uploadedFiles;
    }
    
}