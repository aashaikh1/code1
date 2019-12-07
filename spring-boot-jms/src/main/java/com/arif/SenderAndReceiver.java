package com.arif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

/**
 *
 * @author arif
 */
@SpringBootApplication
@ComponentScan
@EnableJms
public class SenderAndReceiver {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SenderAndReceiver.class, args);
        System.out.println();//to separate from the spring output
        SenderAndReceiver.send(context);
        //SenderAndReceiver.receive(context);//UnComment this like if you want to synchronously receive the response, 
                                            //in that case disable the JmsResponseListener.java by commenting the @Component 
                                            //annotation on JmsResponseListener.java 
    }

    private static void send(ApplicationContext context){
        JmsHelper jmsHelper = context.getBean(JmsHelper.class);
        jmsHelper.sendMessage("Hello there");
    }
    
    private static void receive(ApplicationContext context){
        JmsHelper jmsHelper = context.getBean(JmsHelper.class);
        jmsHelper.receiveMessage();
    }
}