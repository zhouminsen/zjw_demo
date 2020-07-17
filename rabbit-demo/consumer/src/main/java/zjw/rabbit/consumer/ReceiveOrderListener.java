package zjw.rabbit.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author zhoujiawei
 * @ClassName: ReceiveOrderListener
 * @Description: 接收mq消息
 * @date 2019-09-04
 */
@Component
@Slf4j
public class ReceiveOrderListener {


    /**
     * @param message
     * @param channel
     * @throws Exception
     * @Title: onMessage
     * @Description: 接收mq消息
     */
    @RabbitListener(queues = RabbitMqConfig.DELAY_QUEUE)
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            String s = new String(message.getBody());
            log.info("consumer--:" + message.getMessageProperties() + ":" + s);
        } catch (Exception e) {
            log.error("consumer exception {}", e);
            /*//抛弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);*/

           /* //放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);*/
        } finally {
            //不管消费成不成功，一律ack，之后根据数据库记录做补偿
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * @param message
     * @param channel
     * @throws Exception
     * @Title: onMessage
     * @Description: 接收mq消息
     */
    @RabbitListener(queues = "user.order.receive_queue")
    public void onMessage2(Message message, Channel channel) throws Exception {
        try {
            String s = new String(message.getBody());
            log.info("consumer--:" + message.getMessageProperties() + ":" + s);
        } catch (Exception e) {
            log.error("consumer exception {}", e);
            /*//抛弃这条消息
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);*/

           /* //放回队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);*/
        } finally {
            //不管消费成不成功，一律ack，之后根据数据库记录做补偿
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}  
