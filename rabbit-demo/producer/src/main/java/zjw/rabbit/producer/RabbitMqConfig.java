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

    /**
     * ifm的请求队列
     */
    public static final String IFM_REQUEST_QUEUE = "sos_ifm_request_queue";

    public static final String IFM_REQUEST_KEY = "sos_ifm_request_key";

    public static final String IFM_REQUEST_EXCHANGE = "sos_ifm_request_exchange";

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


    @Bean(name = IFM_REQUEST_EXCHANGE)
    public DirectExchange orderExchange() {
        DirectExchange directExchange = new DirectExchange(RabbitMqConfig.IFM_REQUEST_EXCHANGE, true, false);
        return directExchange;
    }

    @Bean(name = IFM_REQUEST_QUEUE)
    public Queue orderQueue() {
        return new Queue(RabbitMqConfig.IFM_REQUEST_QUEUE, true, false, false);
    }

    @Bean
    public Binding bindingQueue() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(IFM_REQUEST_KEY);
    }

}
