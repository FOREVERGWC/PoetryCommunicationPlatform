package com.main.framework.web.service;

import com.main.common.constant.CacheConstants;
import com.main.common.constant.Constants;
import com.main.common.constant.UserConstants;
import com.main.common.core.domain.entity.SysUser;
import com.main.common.core.domain.model.RegisterBody;
import com.main.common.core.redis.RedisCache;
import com.main.common.exception.user.CaptchaException;
import com.main.common.exception.user.CaptchaExpireException;
import com.main.common.utils.MessageUtils;
import com.main.common.utils.SecurityUtils;
import com.main.common.utils.StringUtils;
import com.main.framework.manager.AsyncManager;
import com.main.framework.manager.factory.AsyncFactory;
import com.main.system.service.ISysConfigService;
import com.main.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 注册校验方法
 *
 * @author admin
 */
@Component
public class SysRegisterService {
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 注册
     */
    public String register(RegisterBody registerBody) {
        String msg = "", username = registerBody.getUsername(), password = registerBody.getPassword(), phone = registerBody.getPhone();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);

        // 验证码开关
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        if (captchaEnabled) {
            validateCaptcha(phone, registerBody.getCode());
        }

        if (StringUtils.isEmpty(username)) {
            msg = "用户名不能为空";
        } else if (StringUtils.isEmpty(password)) {
            msg = "用户密码不能为空";
        } else if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            msg = "账户长度必须在2到20个字符之间";
        } else if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            msg = "密码长度必须在5到20个字符之间";
        } else if (!userService.checkUserNameUnique(sysUser)) {
            msg = "保存用户'" + username + "'失败，注册账号已存在";
        } else {
            sysUser.setNickName(username);
            sysUser.setPassword(SecurityUtils.encryptPassword(password));
            sysUser.setPhonenumber(phone);
            boolean regFlag = userService.registerUser(sysUser);
            userService.insertUserAuth(sysUser.getUserId(), new Long[]{2L});
            if (!regFlag) {
                msg = "注册失败,请联系系统管理人员";
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.REGISTER, MessageUtils.message("user.register.success")));
            }
        }
        return msg;
    }

    /**
     * 校验验证码
     *
     * @param phone 手机
     * @param code  验证码
     */
    public void validateCaptcha(String phone, String code) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + phone;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException();
        }
    }
}
