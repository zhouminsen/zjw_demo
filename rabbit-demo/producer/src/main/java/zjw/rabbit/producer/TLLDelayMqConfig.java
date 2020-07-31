package zjw.rabbit.producer;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * 一定要按照过期时间升序进行入队
 * 过期时间降序入队，会导致所有消息都以最长过期时间进行消费
 */
@Configuration
public class TLLDelayMqConfig {

    /**
     * 死信配置
     */
    public static final String DELAY_QUEUE = "delay_queue";

    public static final String DELAY_KEY = "delay_key";

    public static final String DELAY_EXCHANGE = "delay_exchange";


    /**
     * 接收死信配置
     */
    public static final String RECEIVE_QUEUE = "receive_queue";

    public static final String RECEIVE_KEY = "receive_key";

    public static final String RECEIVE_EXCHANGE = "receive_exchange";

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange delayExchange() {
        return new DirectExchange(DELAY_EXCHANGE);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> map = new HashMap<>(16);
        map.put("x-dead-letter-exchange", RECEIVE_EXCHANGE);
        map.put("x-dead-letter-routing-key", RECEIVE_KEY);
        return new Queue(DELAY_QUEUE, true, false, false, map);
    }

    /**
     * 给死信队列绑定交换机
     *
     * @return
     */
    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with(DELAY_KEY);
    }

    /**
     * 死信接收交换机
     *
     * @return
     */
    @Bean
    public DirectExchange receiveExchange() {
        return new DirectExchange(RECEIVE_EXCHANGE);
    }

    /**
     * 死信接收队列
     *
     * @return
     */
    @Bean
    public Queue receiveQueue() {
        return new Queue(RECEIVE_QUEUE);
    }

    /**
     * 死信交换机绑定消费队列
     *
     * @return
     */
    @Bean
    public Binding receiveBinding() {
        return BindingBuilder.bind(receiveQueue()).to(receiveExchange()).with(RECEIVE_KEY);
    }

}