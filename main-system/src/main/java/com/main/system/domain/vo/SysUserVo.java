package com.main.system.domain.vo;

import com.main.common.core.domain.BaseEntity;

import java.util.Date;

public class SysUserVo extends BaseEntity {
    private static final long serialVersionUID = -2700115387494239177L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 用户性别
     */
    private String sex;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    private Date loginDate;

    public SysUserVo() {
    }

    public SysUserVo(Long userId, String userName, String nickName, String sex, String avatar, String delFlag, String loginIp, Date loginDate) {
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.sex = sex;
        this.avatar = avatar;
        this.delFlag = delFlag;
        this.loginIp = loginIp;
        this.loginDate = loginDate;
    }

    public Long getUserId() {
        return userId;
    }

    public SysUserVo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SysUserVo setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public SysUserVo setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public SysUserVo setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public SysUserVo setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public SysUserVo setDelFlag(String delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public SysUserVo setLoginIp(String loginIp) {
        this.loginIp = loginIp;
        return this;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public SysUserVo setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
        return this;
    }
}
