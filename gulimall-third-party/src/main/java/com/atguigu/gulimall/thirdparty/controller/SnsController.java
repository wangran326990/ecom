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
public class SnsController {
    @Autowired
    private SmsVerifyCodeService smsVerifyCodeService;

    @RequestMapping(value = "/sms/sendCode", method = RequestMethod.GET)
    public R sendMessage(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        log.debug("phoneNumber:{}", phone);
        String msg = smsVerifyCodeService.sendSMSMessage(phone, code);
        return R.ok().put("code", msg);
    }
}
