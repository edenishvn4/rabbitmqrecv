package com.irsha.test.rabbitmq_recievedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitmqRecievedemoApplication {

	public static void main(String[] args) throws Exception {

		SpringApplication.run(RabbitmqRecievedemoApplication.class, args);
        Recieve recieve = new Recieve();
		String[] data= {"info","warning"};
        recieve.recieveMQ(data);
	}
}
