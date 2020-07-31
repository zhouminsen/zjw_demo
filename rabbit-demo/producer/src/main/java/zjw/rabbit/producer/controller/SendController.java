package zjw.rabbit.producer.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjw.rabbit.producer.DLXDelayMqConfig;

import java.util.UUID;

/**
 * Created by Administrator on 2020-05-20.
 */
@RequestMapping("/send")
@RestController
public class SendController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("tll")
    public void tll() {

    }

    @GetMapping("dlx")
    public void dlx() {
        String str = "dlx哈哈哈";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(DLXDelayMqConfig.DELAY_EXCHANGE, DLXDelayMqConfig.DELAY_KEY, str + "2", message -> {
            message.getMessageProperties().setHeader("x-delay", 30000);
            return message;
        }, correlationData);
        correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(DLXDelayMqConfig.DELAY_EXCHANGE, DLXDelayMqConfig.DELAY_KEY + "2", str, message -> {
            message.getMessageProperties().setHeader("x-delay", 20000);
            return message;
        }, correlationData);
    }
}
