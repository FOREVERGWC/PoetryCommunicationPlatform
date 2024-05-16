package com.main.common.core.domain.model;

public class WechatLoginBody {
    /**
     * 小程序AppId
     */
    private String appid;
    /**
     * 小程序AppSecret
     */
    private String secret;
    /**
     * 登录code
     */
    private String js_code;
    /**
     * 授权类型
     */
    private String grant_type;

    public String getAppid() {
        return appid;
    }

    public WechatLoginBody setAppid(String appid) {
        this.appid = appid;
        return this;
    }

    public String getSecret() {
        return secret;
    }

    public WechatLoginBody setSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public String getJs_code() {
        return js_code;
    }

    public WechatLoginBody setJs_code(String js_code) {
        this.js_code = js_code;
        return this;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public WechatLoginBody setGrant_type(String grant_type) {
        this.grant_type = grant_type;
        return this;
    }
}
