import org.junit.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import zjw.rabbit.producer.RabbitMqConfig;

import java.util.UUID;

/**
 * Created by zhoujiawei on 2020-07-17.
 */
public class rabbitTest extends BaseTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void sendMq() {
        String str = "哈哈哈";
        String id = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend(RabbitMqConfig.DELAY_EXCHANGE, RabbitMqConfig.DELAY_KEY, str, correlationData);
    }

    @Test
    public void sendDelayMq() {
        String str = "哈哈哈";
        String id = UUID.randomUUID().toString();
        CorrelationData correlationData = new CorrelationData(id);
        rabbitTemplate.convertAndSend("user.order.delay_exchange", "user.order.delay_key", str, message -> {
            message.getMessageProperties().setExpiration("20000");
            return message;
        }, correlationData);
    }
}
