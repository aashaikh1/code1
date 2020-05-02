package com.arif.events.lambda;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.google.gson.JsonObject;
import java.io.IOException;

/**
 *
 * @author arif
 */
public class MailUtil {
    private static final String NEW_LINE = System.getProperty("line.separator");    
    private static final String FROM = "cars.on.aws@gmail.com";
    public static void send(String to, String subject, String body) throws IOException {
        try {
            AmazonSimpleEmailService client
                    = AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.US_WEST_2).build();
            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(to))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(body)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(subject)))
                    .withSource(FROM);
            client.sendEmail(request);
            System.out.println("Email sent to: " + to); //System.out.println goes to cloudwatch
        } catch (Exception ex) {
            System.out.println("Problem sending email: " + ex.getMessage()); //System.out.println goes to cloudwatch
        }
    }
    

    public static String buildMailBody(JsonObject jsonObject){
        String event = jsonObject.get("EVENT").getAsString();
        String body = "";
        switch(event){
            case "ADDCAR":  {
                body = "You have added new car for sale, car details link: " + NEW_LINE + jsonObject.get("URL").getAsString();
            }
            break;
            case "UPDATECAR": {
                body = "You updated your car details, updated car details link: " + NEW_LINE + jsonObject.get("URL").getAsString();
            }
            break;
            case "DELETECAR": {
                body = "You deleted your car: " + NEW_LINE + jsonObject.get("STOCKID").getAsString() 
                        + jsonObject.get("CAR_MAKE").getAsString()
                        + jsonObject.get("CAR_MODEL").getAsString();
            }
            break;
            case "ADDPHOTO": {
                body = "You added photo for your car, updated car details link: " + NEW_LINE + jsonObject.get("URL").getAsString(); 
            }
            break;
            case "DELETEPHOTO": {
                body = "You deleted photo for your car, updated car details link: " + NEW_LINE + jsonObject.get("URL").getAsString(); 
            }
            break;
        }
        return body;
    }    
    
}
