import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.junit.Test;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import zjw.rabbit.producer.DLXDelayMqConfig;
import zjw.rabbit.producer.RabbitMqConfig;
import zjw.rabbit.producer.TLLDelayMqConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

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
        rabbitTemplate.convertAndSend(RabbitMqConfig.NORMAL_EXCHANGE, RabbitMqConfig.NORMAL_KEY, str, correlationData);
    }

    /**
     * 一定要按照过期时间升序进行入队
     * 过期时间降序入队，会导致所有消息都以最长过期时间进行消费
     */
    @Test
    public void sendTllDelayMq() {
        String str = "tll哈哈哈";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(TLLDelayMqConfig.DELAY_EXCHANGE, TLLDelayMqConfig.DELAY_KEY, str + "1", message -> {
            message.getMessageProperties().setExpiration("30000");
            return message;
        }, correlationData);
        correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(TLLDelayMqConfig.DELAY_EXCHANGE, TLLDelayMqConfig.DELAY_KEY, str + "2", message -> {
            message.getMessageProperties().setExpiration("20000");
            return message;
        }, correlationData);
    }

    /**
     * 同个线程一次只能发送一个延迟消息
     */
    @Test
    public void sendDlxDelayMq() {
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


    @Autowired
    private ConnectionFactory connectionFactory;

    @Test
    public void dlxSendDelayMq() throws IOException, TimeoutException {
        org.springframework.amqp.rabbit.connection.Connection connection = connectionFactory.createConnection();
        Channel channel = connection.createChannel(false);
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        channel.exchangeDeclare("delay.plugin.exchange", "x-delayed-message", true, false, arguments);
        channel.queueDeclare("delay.plugin.queue", true, false, false, null);
        channel.queueBind("delay.plugin.queue", "delay.plugin.exchange", "delay.plugin.routingKey");
        Map<String, Object> headers = new HashMap<>();
        //延迟10s后发送
        headers.put("x-delay", 10000);
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.headers(headers);
        channel.basicPublish("delay.plugin.exchange", "delay.plugin.routingKey", builder.build(), "该消息将在10s后发送到队列".getBytes());
        channel.close();
        connection.close();
    }
}
