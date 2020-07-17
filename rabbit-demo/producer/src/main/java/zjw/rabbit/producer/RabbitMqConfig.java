package zjw.rabbit.producer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author Frank
 *         <p>
 *         MQ 配置
 */
//@Configuration
public class RabbitMqConfig {

    /**
     * ifm的请求队列
     */
    public static final String DELAY_QUEUE = "delay_queue";

    public static final String DELAY_KEY = "delay_key";

    public static final String DELAY_EXCHANGE = "delay_exchange";

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


    @Bean(name = DELAY_EXCHANGE)
    public DirectExchange orderExchange() {
        DirectExchange directExchange = new DirectExchange(RabbitMqConfig.DELAY_EXCHANGE, true, false);
        return directExchange;
    }

    @Bean(name = DELAY_QUEUE)
    public Queue orderQueue() {
        return new Queue(RabbitMqConfig.DELAY_QUEUE, true, false, false);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(DELAY_KEY);
    }

}
