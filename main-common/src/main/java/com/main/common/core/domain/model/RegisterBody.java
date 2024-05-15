package com.main.common.core.domain.model;

/**
 * 用户注册对象
 *
 * @author admin
 */
public class RegisterBody extends LoginBody {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public RegisterBody setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
