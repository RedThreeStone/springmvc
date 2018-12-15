package it.lei.boot.rabbitMq.sub;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "fanout.B")
public class SubConsumer2 {
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer2:"+message);
    }
}
