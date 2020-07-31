package zjw.rabbit.consumer;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Frank
 *         <p>
 *         MQ 配置
 */
@Configuration
public class RabbitMqConfig {

    public static final String NORMAL_QUEUE = "normal_queue";

    public static final String RECEIVE_QUEUE = "receive_queue";

    public static final String DELAY_PLUGIN_QUEUE = "delay.plugin.queue";


    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainerOne() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        //手动ack
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleMessageListenerContainer;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setConfirmCallback(new MsgConfirmCallBack());
        return template;
    }

    @Bean
    public Queue normalQueue() {
        return new Queue(NORMAL_QUEUE);
    }

    @Bean
    public Queue receiveQueue() {
        return new Queue(RECEIVE_QUEUE);
    }


    @Bean
    public Queue delayPluginQueue() {
        return new Queue(DELAY_PLUGIN_QUEUE);
    }

}
