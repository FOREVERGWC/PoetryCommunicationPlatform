package com.main.web.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.main.common.constant.Constants;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.domain.entity.SysMenu;
import com.main.common.core.domain.entity.SysUser;
import com.main.common.core.domain.model.LoginBody;
import com.main.common.exception.ServiceException;
import com.main.common.utils.SecurityUtils;
import com.main.framework.web.service.SysLoginService;
import com.main.framework.web.service.SysPermissionService;
import com.main.system.service.ISysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author admin
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(), loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    @PostMapping("/login/wechat")
    public AjaxResult wechatLogin(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        Map<String, Object> map = BeanUtil.beanToMap(loginBody);
        try (HttpResponse response = HttpUtil.createGet("https://api.weixin.qq.com/sns/jscode2session").form(map).execute()) {
            ajax.put("body", response.body());
            return ajax;
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
