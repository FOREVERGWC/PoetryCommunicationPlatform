package com.main.common.core.domain.model;

public class WechatResponse {
    private Integer errcode;
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public WechatResponse setErrcode(Integer errcode) {
        this.errcode = errcode;
        return this;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public WechatResponse setErrmsg(String errmsg) {
        this.errmsg = errmsg;
        return this;
    }
}
