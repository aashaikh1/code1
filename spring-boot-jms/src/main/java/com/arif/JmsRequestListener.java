package com.arif;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import javax.jms.JMSException;
import javax.jms.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 *
 * @author arif
 */
@Component
public class JmsRequestListener {

    @Autowired
    private JmsMessageHandler messageHandler;

    @Autowired
    private JmsHelper jmsHelper;
    
    @JmsListener(destination = "${input.queue.name}")
    @SendTo("${reply.queue.name}")
    public Message consumeMesssage(Message message) throws JMSException {
        String payload = null;
        String replyPayload = "Your Message has been received";
        messageHandler.handleRequestReceivedMessage(message);
        Message replyMessage = jmsHelper.createMessage("Your Message has been received", message.getJMSMessageID());//set correlation id
        return replyMessage;
    }

}
