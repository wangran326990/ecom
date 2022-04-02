package com.atguigu.gulimall.thirdparty.controller;

import com.atguigu.common.utils.R;
import com.atguigu.gulimall.thirdparty.service.SmsVerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SNSController {
    @Autowired
    private SmsVerifyCodeService smsVerifyCodeService;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public R sendMessage(@RequestParam String phoneNumber) {
        log.debug("phoneNumber:{}", phoneNumber);
        String msg = smsVerifyCodeService.sendSMSMessage(phoneNumber);
        return R.ok().put("code", msg);
    }
}
