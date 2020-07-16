package zjw.rabbit.producer;

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

        if (ack) {
            // 2019-09-15 该条记录从数据中删除
            logger.debug("message send success");
        } else {
        	//error manage !
            logger.debug("message send failure");
        }
    }

}