package com.ruider.controller;

import com.ruider.common.Result;
import com.ruider.utils.ActiveMQ.ActiveMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;

/**
 * ActiveMQ controller消息中间件
 */
@RestController
@RequestMapping("message")
public class ActiveMQController {
    private final Logger logger = LoggerFactory.getLogger(ActiveMQController.class);

    @Autowired
    private Queue queue;

    @Autowired
    private ActiveMQProducer activeMQProducerTest;

    @GetMapping("activeMQProduce")
    public Result activeMQProduce (String message) {
        Result result = new Result();
        try {
            activeMQProducerTest.send(this.queue , message);
            result.setIsSuccess(true);
            result.setMessage("发送消息成功，MQ收到消息");
            logger.info("【activeMQProduce success】发送消息成功，MQ收到消息");
            return result;
        }
        catch(Exception e) {
            result.setIsSuccess(false);
            result.setCode(Result.EXCEPTION_CODE);
            result.setMessage("发送消息失败，MQ未收到消息");
            logger.error("【activeMQProduce fail】发送消息失败，MQ收到消息"+ e);
            return result;
        }
    }


}
