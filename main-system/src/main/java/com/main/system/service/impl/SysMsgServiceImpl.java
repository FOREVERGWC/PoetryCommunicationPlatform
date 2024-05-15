package com.main.system.service.impl;

import cn.hutool.json.JSONUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.main.common.exception.ServiceException;
import com.main.system.service.ISysMsgService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysMsgServiceImpl implements ISysMsgService {
    final String accessKeyId = "LTAI5tKtCzHjhCcNnxwyq2QS";
    final String accessKeySecret = "DrKeJI5ZRcfH0fHc8Kb1ufiacDlJrs";
    final String signName = "阿里云短信测试";
    final String templateCode = "SMS_154950909";

    @Override
    public void sendPhoneMsg(String phones, String param) {
        Map<String, String> map = new HashMap<>();
        map.put("code", param);
        try {
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
            IAcsClient acsClient = new DefaultAcsClient(profile);
            SendSmsRequest request = new SendSmsRequest();
            request.setSysRegionId("cn-hangzhou");
            request.setPhoneNumbers(phones);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            request.setTemplateParam(JSONUtil.toJsonStr(map));
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
            System.out.println("发送验证码：" + sendSmsResponse.getMessage());
            System.out.println("发送验证码：" + param);
        } catch (ClientException e) {
            System.out.println("验证码发送失败：" + e);
            throw new ServiceException("验证码发送失败！");
        }
    }
}
