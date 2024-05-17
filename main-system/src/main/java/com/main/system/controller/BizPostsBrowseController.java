package com.main.system.controller;

import com.main.common.annotation.Anonymous;
import com.main.common.annotation.Log;
import com.main.common.core.controller.BaseController;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.page.TableDataInfo;
import com.main.common.enums.BusinessType;
import com.main.common.utils.poi.ExcelUtil;
import com.main.system.domain.BizPostsBrowse;
import com.main.system.service.IBizPostsBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 帖子浏览记录Controller
 *
 * @author admin
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/system/browse")
public class BizPostsBrowseController extends BaseController {
    @Autowired
    private IBizPostsBrowseService bizPostsBrowseService;

    /**
     * 查询帖子浏览记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:browse:list')")
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(BizPostsBrowse bizPostsBrowse) {
        startPage();
        List<BizPostsBrowse> list = bizPostsBrowseService.selectBizPostsBrowseList(bizPostsBrowse);
        return getDataTable(list);
    }

    /**
     * 导出帖子浏览记录列表
     */
    @PreAuthorize("@ss.hasPermi('system:browse:export')")
    @Log(title = "帖子浏览记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPostsBrowse bizPostsBrowse) {
        List<BizPostsBrowse> list = bizPostsBrowseService.selectBizPostsBrowseList(bizPostsBrowse);
        ExcelUtil<BizPostsBrowse> util = new ExcelUtil<>(BizPostsBrowse.class);
        util.exportExcel(response, list, "帖子浏览记录数据");
    }

    /**
     * 获取帖子浏览记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:browse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bizPostsBrowseService.selectBizPostsBrowseById(id));
    }

    /**
     * 新增帖子浏览记录
     */
    @PreAuthorize("@ss.hasPermi('system:browse:add')")
    @Log(title = "帖子浏览记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPostsBrowse bizPostsBrowse) {
        return toAjax(bizPostsBrowseService.insertBizPostsBrowse(bizPostsBrowse));
    }

    /**
     * 修改帖子浏览记录
     */
    @PreAuthorize("@ss.hasPermi('system:browse:edit')")
    @Log(title = "帖子浏览记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPostsBrowse bizPostsBrowse) {
        return toAjax(bizPostsBrowseService.updateBizPostsBrowse(bizPostsBrowse));
    }

    /**
     * 删除帖子浏览记录
     */
    @PreAuthorize("@ss.hasPermi('system:browse:remove')")
    @Log(title = "帖子浏览记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizPostsBrowseService.deleteBizPostsBrowseByIds(ids));
    }
}
