package zjw.rabbit.producer.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zjw.rabbit.producer.DLXDelayMqConfig;
import zjw.rabbit.producer.TLLDelayMqConfig;

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
        String str = "tll哈哈哈";
        rabbitTemplate.convertAndSend(TLLDelayMqConfig.DELAY_EXCHANGE, TLLDelayMqConfig.DELAY_KEY, str + "2", message -> {
            message.getMessageProperties().setExpiration("20000");
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
        rabbitTemplate.convertAndSend(TLLDelayMqConfig.DELAY_EXCHANGE, TLLDelayMqConfig.DELAY_KEY, str + "2", message -> {
            message.getMessageProperties().setExpiration("25000");
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
        rabbitTemplate.convertAndSend(TLLDelayMqConfig.DELAY_EXCHANGE, TLLDelayMqConfig.DELAY_KEY, str + "1", message -> {
            message.getMessageProperties().setExpiration("30000");
            return message;
        }, new CorrelationData(UUID.randomUUID().toString()));
    }

    @GetMapping("dlx")
    public void dlx(Integer interval) {
        String str = "dlx哈哈哈";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(DLXDelayMqConfig.DELAY_EXCHANGE, DLXDelayMqConfig.DELAY_KEY, str + "2", message -> {
            message.getMessageProperties().setHeader("x-delay", interval);
            return message;
        }, correlationData);
        correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(DLXDelayMqConfig.DELAY_EXCHANGE, DLXDelayMqConfig.DELAY_KEY + "2", str, message -> {
            message.getMessageProperties().setHeader("x-delay", interval);
            return message;
        }, correlationData);
    }
}
