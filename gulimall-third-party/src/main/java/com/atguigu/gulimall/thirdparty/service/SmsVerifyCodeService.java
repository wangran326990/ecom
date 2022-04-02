package com.atguigu.gulimall.thirdparty.service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.atguigu.common.utils.IdentifyCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class SmsVerifyCodeService {

    @Autowired
    private AmazonSNS amazonSns;

    public String sendSMSMessage(String phoneNumber) {
        //String message = "My SMS message";
        Map<String, MessageAttributeValue> smsAttributes =
                new HashMap<>();
        //<set SMS attributes>
        smsAttributes.put("AWS.SNS.SMS.MaxPrice", new MessageAttributeValue()
                .withStringValue("0.05") //Sets the max price to 0.50 USD.
                .withDataType("Number"));

        //get VerifyCode.
        String message = IdentifyCodeUtil.getRandom();

        PublishResult result = amazonSns.publish(new PublishRequest()
                .withMessage(message)
                .withPhoneNumber(phoneNumber)
                .withMessageAttributes(smsAttributes));
        log.info("message ID:{}",result.getMessageId()); // Prints the message ID.
        return message;
    }
}
