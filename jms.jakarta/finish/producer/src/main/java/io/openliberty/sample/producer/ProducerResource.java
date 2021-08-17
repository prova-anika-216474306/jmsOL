// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2021 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
package io.openliberty.sample.producer;

import org.apache.activemq.*;
import jakarta.jms.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/produce")
public class ProducerResource {

    @POST
    @Path("message")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // Name of the queue we will be sending messages to
    private static String subject = "TESTQUEUE";

    @Override
    public void run() {
        while (true) {
            try {
                // Getting JMS connection from the server and starting it
                ConnectionFactory connectionFactory =
                        new ActiveMQConnectionFactory(url);
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // JMS messages are sent and received using a Session. We will
                // create here a non-transactional session object. If you want
                // to use transactions you should set the first parameter to 'true'
                Session session = connection.createSession(false,
                        Session.AUTO_ACKNOWLEDGE);

                // Destination represents here our queue 'TESTQUEUE' on the
                // JMS server. You don't have to do anything special on the
                // server to create it, it will be created automatically.
                Destination destination = session.createQueue(subject);

                // MessageProducer is used for sending messages (as opposed
                // to MessageConsumer which is used for receiving them)
                
                MessageProducer producer = session.createProducer(queue);
                MessageProducer producer = session.createProducer(topic);
                MessageProducer producer = session.createProducer(destination);

                
                //The send message method
                public void onMessage(Message message) {
                try {
                    // We will send a small text message saying 'Hello' in Japanese
                        TextMessage response = this.session.createTextMessage();
                        if (message instanceof TextMessage) {
                        TextMessage txtMsg = (TextMessage) message;
                        String messageText = txtMsg.getText();
                    response.setText(this.messageProtocol.handleProtocolMessage(messageText));
            }
            //Set the correlation ID from the received message to be the correlation id of the response message
            //this lets the client identify which message this is a response to if it has more than
            //one outstanding message to the server
            response.setJMSCorrelationID(message.getJMSCorrelationID());

            //Send the response to the Destination specified by the JMSReplyTo field of the received message,
            //this is presumably a temporary queue created by the client
            this.replyProducer.send(message.getJMSReplyTo(), response);
        } 
                    catch (JMSException e) {
            //Handle the exception appropriately
        }
                    

            //Set the correlation ID from the received message to be the correlation id of the response message
            //this lets the client identify which message this is a response to if it has more than
            //one outstanding message to the server
            response.setJMSCorrelationID(message.getJMSCorrelationID());

            //Send the response to the Destination specified by the JMSReplyTo field of the received message,
            //this is presumably a temporary queue created by the client
            this.replyProducer.send(message.getJMSReplyTo(), response);
                TextMessage message = session.createTextMessage("Hello consumer");

                // Here we are sending the message!
                producer.send(message);
                System.out.println("Sent message '" + message.getText() + "'");

                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public Response postMsg(String text) {
    	// TODO: post message to activemq
    	System.out.println("post message to activemq: " + text);
        return Response.ok().build();
    }
}
