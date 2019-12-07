package com.arif;

import javax.jms.Message;
import javax.jms.TextMessage;
import org.springframework.stereotype.Component;

/**
 *
 * @author arif
 */
@Component
public class PrintHandler implements JmsMessageHandler{
    
    public void handleResponseSentMessage(Message message){
        try{
            String payload = null;
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                payload = textMessage.getText();
                System.out.println("Response Sent: " + payload  
                        + " MessageId: " + message.getJMSMessageID()
                        + " CorrelationId: " + message.getJMSCorrelationID());
            }else{
                System.out.println("Response Sent: " + message);//In this project this else condition will not happen so just print the whole message in case of other types
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void handleResponseReceivedMessage(Message message){
        try{
            String payload = null;
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                payload = textMessage.getText();
                System.out.println("Response Received: " + payload  
                        + " MessageId: " + message.getJMSMessageID()
                        + " CorrelationId: " + message.getJMSCorrelationID());
            }else{
                System.out.println("Response Received: " + message);//In this project this else condition will not happen so just print the whole message in case of other types
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public void handleRequestSentMessage(Message message){
        try{
            String payload = null;
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                payload = textMessage.getText();
                System.out.println("Request Sent: " + payload + " MessageId: " + message.getJMSMessageID());
            }else{
                System.out.println("Request Sent: " + message);//In this project this else condition will not happen so just print the whole message in case of other types
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    

    public void handleRequestReceivedMessage(Message message){
        try{
            String payload = null;
            if(message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                payload = textMessage.getText();
                System.out.println("Request Received: " + payload + " MessageId: " + message.getJMSMessageID());
            }else{
                System.out.println("Request Received: " + message);//In this project this else condition will not happen so just print the whole message in case of other types
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

}
