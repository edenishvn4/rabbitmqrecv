package com.irsha.test.rabbitmq_recievedemo;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recieve {
    private final static String EXC_NAME = "exchange";

    public void recieveMQ(String[] txt) throws Exception,InterruptedException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXC_NAME, BuiltinExchangeType.DIRECT);
        String queueName = channel.queueDeclare().getQueue();

        if (txt.length < 1){
            System.err.println("Usage: [info] ");
            System.exit(1);
        }

        for(String severity : txt){
            channel.queueBind(queueName, EXC_NAME, severity);
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    Thread.sleep(3000);
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" +envelope.getRoutingKey()+ " : " +message + "'");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}
