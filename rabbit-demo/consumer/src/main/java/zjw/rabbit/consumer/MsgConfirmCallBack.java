package zjw.rabbit.consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

/**
 * 消息确认器
 */
class MsgConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    Logger logger = LogManager.getLogger(MsgConfirmCallBack.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.debug("MsgConfirmCallBack, id: " + correlationData);
        if(ack){
            logger.debug("message send success");
        }else{
            //error manage !
            logger.debug("message send failure");
        }
    }

}