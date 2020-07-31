package zjw.rabbit.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class DLXDelayMqConfig {

    /**
     * 死信配置
     */
    public static final String DELAY_QUEUE = "delay.plugin.queue";

    public static final String DELAY_KEY = "delay.plugin.routingKey";

    public static final String DELAY_EXCHANGE = "delay.plugin.exchange";


    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public CustomExchange dlxDelayExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange(DELAY_EXCHANGE, "x-delayed-message", true, false, arguments);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue dlxDelayQueue() {
        return new Queue(DELAY_QUEUE, true, false, false);
    }

    /**
     * 给死信队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding dlxDelayBinding() {
        return BindingBuilder.bind(dlxDelayQueue()).to(dlxDelayExchange()).with(DELAY_KEY).noargs();
    }
}