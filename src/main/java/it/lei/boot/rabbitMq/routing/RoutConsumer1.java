package it.lei.boot.rabbitMq.routing;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "router.A")
public class RoutConsumer1 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer1:"+message);
    }
}
