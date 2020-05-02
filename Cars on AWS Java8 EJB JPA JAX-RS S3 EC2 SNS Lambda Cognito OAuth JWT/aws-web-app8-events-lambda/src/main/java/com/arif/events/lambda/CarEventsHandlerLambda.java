package com.arif.events.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author arif
 */
public class CarEventsHandlerLambda implements RequestHandler<SNSEvent, Void>{
    @Override
    public Void handleRequest(SNSEvent event, Context ctx) {
        SNSEvent.SNSRecord msg = null;
        try {
            if(!event.getRecords().isEmpty()){
                msg = event.getRecords().get(0);
                JsonObject jsonObject = new JsonParser().parse(msg.getSNS().getMessage()).getAsJsonObject();
                MailUtil.send(jsonObject.get("EMAIL").getAsString(), "Arif Cars WebApp Notification", MailUtil.buildMailBody(jsonObject));
            }
            return null;
        }catch(Exception e){
            System.out.println("Problem in CarEventsHandlerLambda.handleRequest: " + e.getMessage() 
                    + " OriginalMessage: " + msg.getSNS().getMessage()); //System.out.println goes to cloudwatch
        }
        return null;
    }
}