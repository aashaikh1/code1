package com.arif;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author arif
 */
@Component
public class JmsResponseListener {

    @Autowired
    private JmsMessageHandler messageHandler;

    @Autowired
    private JmsHelper jmsHelper;
    
    @JmsListener(destination = "${reply.queue.name}")
    public void consumeMesssage(Message message) throws JMSException {
        messageHandler.handleResponseReceivedMessage(message);
    }

}
