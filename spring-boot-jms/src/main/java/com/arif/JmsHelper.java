package com.arif;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Component;

/**
 *
 * @author arif
 */
@Component
public class JmsHelper implements ApplicationContextAware{
    
    @Value("${input.queue.name}")
    private String IN_QUEUE;
    
    @Value("${reply.queue.name}")
    private String REPLY_QUEUE;
    
    private ApplicationContext context;
    
    @Autowired
    private JmsMessageHandler messageHandler;
    
    public void sendMessage(final String strMessage){
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        jmsTemplate.execute(new SessionCallback(){
            @Override
            public Object doInJms(Session session) throws JMSException {
                Destination dest = session.createQueue(IN_QUEUE);
                MessageProducer producer = session.createProducer(dest);
                TextMessage message = session.createTextMessage();
                message.setText(strMessage);
                producer.send(message);
                messageHandler.handleRequestSentMessage(message);
                return "";
            }
        });
    }

    public void receiveMessage(){
        try{
            String payload = null;
            JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
            System.out.println("Receiver Waiting...");
            Message message = jmsTemplate.receive(REPLY_QUEUE);
            messageHandler.handleResponseReceivedMessage(message);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        this.context = ac;
    }
    
    public Message createMessage(final String payload, String correlationId){
        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);
        Message message = (Message)jmsTemplate.execute(new SessionCallback(){
            @Override
            public Object doInJms(Session session) throws JMSException {
                TextMessage message = session.createTextMessage();
                message.setText(payload);
                message.setJMSCorrelationID(correlationId);
                return message;
            }
        });
        return message;
    }
    
}
