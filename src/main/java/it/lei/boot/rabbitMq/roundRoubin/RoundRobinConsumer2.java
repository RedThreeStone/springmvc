package it.lei.boot.rabbitMq.roundRoubin;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "roundRobinQueue")
public class RoundRobinConsumer2{
    @RabbitHandler
    public  void  doWork(String message){
        System.out.println("Consumer2:"+message);
    }
}
