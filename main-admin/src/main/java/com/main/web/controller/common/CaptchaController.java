package com.main.web.controller.common;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import com.main.common.config.MainConfig;
import com.main.common.constant.CacheConstants;
import com.main.common.constant.Constants;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.redis.RedisCache;
import com.main.common.exception.ServiceException;
import com.main.common.utils.sign.Base64;
import com.main.common.utils.uuid.IdUtils;
import com.main.system.service.ISysConfigService;
import com.main.system.service.ISysMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author admin
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private ISysMsgService sysMsgService;

    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response) throws IOException {
        AjaxResult ajax = AjaxResult.success();
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }

        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = MainConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

    /**
     * 生成手机验证码
     */
    @GetMapping("/phone/captchaImage")
    public AjaxResult getPhoneCode(@RequestParam String phone, HttpServletResponse response) {
        AjaxResult ajax = AjaxResult.success("验证码发送成功！请注意查收");
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        ajax.put("captchaEnabled", captchaEnabled);
        if (!captchaEnabled) {
            return ajax;
        }
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + phone;
        // 检查缓存是否已发送
        String result = redisCache.getCacheObject(verifyKey);
        if (StrUtil.isNotBlank(result)) {
            throw new ServiceException("验证码已发送！请检查短信");
        }
        // 保存验证码信息
        String code = RandomUtil.randomNumbers(4);
        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 发送短信
        sysMsgService.sendPhoneMsg(phone, code);
        return ajax;
    }
}
