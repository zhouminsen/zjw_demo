package zjw.rabbit.producer;

import org.springframework.amqp.core.*;
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

    public static final String NORMAL_KEY = "normal_key";

    public static final String NORMAL_EXCHANGE = "normal_exchange";

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


    @Bean(name = NORMAL_EXCHANGE)
    public DirectExchange normalExchange() {
        DirectExchange directExchange = new DirectExchange(RabbitMqConfig.NORMAL_EXCHANGE, true, false);
        return directExchange;
    }

    @Bean(name = NORMAL_QUEUE)
    public Queue normalQueue() {
        return new Queue(RabbitMqConfig.NORMAL_QUEUE, true, false, false);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(normalQueue()).to(normalExchange()).with(NORMAL_KEY);
    }

}
