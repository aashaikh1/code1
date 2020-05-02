
package com.arif;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author arif
 */
@Stateless
public class SNSEvents implements SNSEventsLocal {

    private AmazonSNS snsClient;
    private String topicArn = CarAppContext.getInstance().getEVENT_TOPIC();
    
    @PostConstruct
    private void init(){
        snsClient = AmazonSNSClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
    }
    
    public void send(String message){
        try{
            PublishRequest publishRequest = new PublishRequest(topicArn, message);
            PublishResult publishResponse = snsClient.publish(publishRequest);        
        }catch (Exception e){
            System.out.println("Problem in SNSEvents.send. Unable to send AWS SNS Notification");
            e.printStackTrace();
        }       
    }
}
