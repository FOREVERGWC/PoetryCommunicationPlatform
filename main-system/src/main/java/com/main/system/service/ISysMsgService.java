package com.main.system.service;

public interface ISysMsgService {
    /**
     * 阿里云短信服务
     *
     * @param phones 手机号列表
     * @param param  参数
     */
    void sendPhoneMsg(String phones, String param);
}
