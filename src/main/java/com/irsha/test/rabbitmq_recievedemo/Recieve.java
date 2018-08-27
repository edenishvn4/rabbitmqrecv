package com.irsha.test.rabbitmq_recievedemo;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recieve {
    private final static String QUEUE_NAME = "durable";

    public void recieveMQ() throws Exception,InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    Thread.sleep(3000);
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
