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
package io.openliberty.sample.consumer;

import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.*;
import jakarta.jms.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/consume")
public class ConsumerResource {

    @GET
    @Path("messages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessages() {
    	List<String> messages = new ArrayList<>();
        // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // Name of the queue we will receive messages from
    private static String subject = "TESTQUEUE";
    @Override
    public void run() {
        while (true) {
            try {
                // Getting JMS connection from the server
                ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Creating session for seding messages
                Session session = connection.createSession(false,
                        Session.AUTO_ACKNOWLEDGE);

                // Getting the queue 'TESTQUEUE'
                Destination destination = session.createQueue(subject);

                // MessageConsumer is used for receiving (consuming) messages
                MessageConsumer consumer = session.createConsumer(destination);

                // Here we receive the message.
                // By default this call is blocking, which means it will wait
                // for a message to arrive on the queue.
                javax.jms.Message message;
                message = consumer.receive();

                // There are many types of Message and TextMessage
                // is just one of them. Producer sent us a TextMessage
                // so we must cast to it to get access to its .getText()
                // method.
                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("Received message '"
                            + textMessage.getText() + "'");
                }
                connection.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("get messages from activemq");
            return Response.ok().entity(messages).build();
        }
    }
    }
}
