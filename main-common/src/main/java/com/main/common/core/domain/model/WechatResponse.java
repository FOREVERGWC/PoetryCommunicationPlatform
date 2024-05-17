package com.main.common.core.domain.model;

import java.io.Serializable;

public class WechatResponse implements Serializable {
    private static final long serialVersionUID = 7808780638744847710L;
    private Integer errcode;
    private String errmsg;
    /**
     * 用户唯一标识码
     */
    private String openid;
    private String unionid;
    /**
     * 会话密钥
     */
    private String session_key;

    public WechatResponse() {
    }

    public WechatResponse(Integer errcode, String errmsg, String openid, String unionid, String session_key) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.openid = openid;
        this.unionid = unionid;
        this.session_key = session_key;
    }

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

    public String getOpenid() {
        return openid;
    }

    public WechatResponse setOpenid(String openid) {
        this.openid = openid;
        return this;
    }

    public String getUnionid() {
        return unionid;
    }

    public WechatResponse setUnionid(String unionid) {
        this.unionid = unionid;
        return this;
    }

    public String getSession_key() {
        return session_key;
    }

    public WechatResponse setSession_key(String session_key) {
        this.session_key = session_key;
        return this;
    }
}
