package it.lei.boot.rabbitMq.simple;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "springsimple")
public class Consumer1 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer1:"+message);
    }
}
