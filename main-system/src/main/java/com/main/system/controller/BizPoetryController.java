package com.main.system.controller;

import com.main.common.annotation.Log;
import com.main.common.core.controller.BaseController;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.page.TableDataInfo;
import com.main.common.enums.BusinessType;
import com.main.common.utils.poi.ExcelUtil;
import com.main.system.domain.BizPoetry;
import com.main.system.service.IBizPoetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 诗词Controller
 *
 * @author admin
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/system/poetry")
public class BizPoetryController extends BaseController {
    @Autowired
    private IBizPoetryService bizPoetryService;

    /**
     * 查询诗词列表
     */
//    @PreAuthorize("@ss.hasPermi('system:poetry:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPoetry bizPoetry) {
        startPage();
        List<BizPoetry> list = bizPoetryService.selectBizPoetryList(bizPoetry);
        return getDataTable(list);
    }

    /**
     * 导出诗词列表
     */
    @PreAuthorize("@ss.hasPermi('system:poetry:export')")
    @Log(title = "诗词", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPoetry bizPoetry) {
        List<BizPoetry> list = bizPoetryService.selectBizPoetryList(bizPoetry);
        ExcelUtil<BizPoetry> util = new ExcelUtil<BizPoetry>(BizPoetry.class);
        util.exportExcel(response, list, "诗词数据");
    }

    /**
     * 获取诗词详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:poetry:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bizPoetryService.selectBizPoetryById(id));
    }

    /**
     * 新增诗词
     */
    @PreAuthorize("@ss.hasPermi('system:poetry:add')")
    @Log(title = "诗词", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPoetry bizPoetry) {
        return toAjax(bizPoetryService.insertBizPoetry(bizPoetry));
    }

    /**
     * 修改诗词
     */
    @PreAuthorize("@ss.hasPermi('system:poetry:edit')")
    @Log(title = "诗词", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPoetry bizPoetry) {
        return toAjax(bizPoetryService.updateBizPoetry(bizPoetry));
    }

    /**
     * 阅读诗词
     */
    @PreAuthorize("@ss.hasPermi('system:poetry:query')")
    @Log(title = "诗词", businessType = BusinessType.UPDATE)
    @PutMapping("/read/{id}")
    public AjaxResult read(@PathVariable Long id) {
        return success(bizPoetryService.readBizPoetry(id));
    }

    /**
     * 删除诗词
     */
    @PreAuthorize("@ss.hasPermi('system:poetry:remove')")
    @Log(title = "诗词", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizPoetryService.deleteBizPoetryByIds(ids));
    }
}
