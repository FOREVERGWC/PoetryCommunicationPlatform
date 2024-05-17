package com.main.system.controller;

import com.main.common.annotation.Anonymous;
import com.main.common.annotation.Log;
import com.main.common.core.controller.BaseController;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.page.TableDataInfo;
import com.main.common.enums.BusinessType;
import com.main.common.utils.poi.ExcelUtil;
import com.main.system.domain.BizPoetryFavorite;
import com.main.system.service.IBizPoetryFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户诗词收藏Controller
 *
 * @author admin
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/system/favorite")
public class BizPoetryFavoriteController extends BaseController {
    @Autowired
    private IBizPoetryFavoriteService bizPoetryFavoriteService;

    /**
     * 查询用户诗词收藏列表
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:list')")
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(BizPoetryFavorite bizPoetryFavorite) {
        startPage();
        List<BizPoetryFavorite> list = bizPoetryFavoriteService.selectBizPoetryFavoriteList(bizPoetryFavorite);
        return getDataTable(list);
    }

    /**
     * 导出用户诗词收藏列表
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:export')")
    @Log(title = "用户诗词收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPoetryFavorite bizPoetryFavorite) {
        List<BizPoetryFavorite> list = bizPoetryFavoriteService.selectBizPoetryFavoriteList(bizPoetryFavorite);
        ExcelUtil<BizPoetryFavorite> util = new ExcelUtil<BizPoetryFavorite>(BizPoetryFavorite.class);
        util.exportExcel(response, list, "用户诗词收藏数据");
    }

    /**
     * 获取用户诗词收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bizPoetryFavoriteService.selectBizPoetryFavoriteById(id));
    }

    /**
     * 获取用户诗词收藏详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:query')")
    @GetMapping(value = "/info/{poetryId}")
    public AjaxResult getInfoByPoetryId(@PathVariable("poetryId") Long poetryId) {
        return success(bizPoetryFavoriteService.selectBizPoetryFavoriteByPoetryId(poetryId));
    }

    /**
     * 新增用户诗词收藏
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:add')")
    @Log(title = "用户诗词收藏", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPoetryFavorite bizPoetryFavorite) {
        return toAjax(bizPoetryFavoriteService.insertBizPoetryFavorite(bizPoetryFavorite));
    }

    /**
     * 修改用户诗词收藏
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:edit')")
    @Log(title = "用户诗词收藏", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPoetryFavorite bizPoetryFavorite) {
        return toAjax(bizPoetryFavoriteService.updateBizPoetryFavorite(bizPoetryFavorite));
    }

    /**
     * 删除用户诗词收藏
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:remove')")
    @Log(title = "用户诗词收藏", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizPoetryFavoriteService.deleteBizPoetryFavoriteByIds(ids));
    }

    /**
     * 用户诗词收藏、取消
     */
    @PreAuthorize("@ss.hasPermi('system:favorite:edit')")
    @Log(title = "用户诗词收藏", businessType = BusinessType.UPDATE)
    @PutMapping("/do/{poetryId}")
    public AjaxResult favorite(@PathVariable Long poetryId) {
        return success(bizPoetryFavoriteService.favorite(poetryId));
    }
}
