package com.main.system.controller;

import com.main.common.annotation.Anonymous;
import com.main.common.annotation.Log;
import com.main.common.core.controller.BaseController;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.page.TableDataInfo;
import com.main.common.enums.BusinessType;
import com.main.common.utils.poi.ExcelUtil;
import com.main.system.domain.BizPoetryComment;
import com.main.system.service.IBizPoetryCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 诗词评论Controller
 *
 * @author admin
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/system/comment")
public class BizPoetryCommentController extends BaseController {
    @Autowired
    private IBizPoetryCommentService bizPoetryCommentService;

    /**
     * 查询诗词评论列表
     */
//    @PreAuthorize("@ss.hasPermi('system:comment:list')")
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(BizPoetryComment bizPoetryComment) {
        startPage();
        List<BizPoetryComment> list = bizPoetryCommentService.selectBizPoetryCommentList(bizPoetryComment);
        return getDataTable(list);
    }

    /**
     * 导出诗词评论列表
     */
    @PreAuthorize("@ss.hasPermi('system:comment:export')")
    @Log(title = "诗词评论", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPoetryComment bizPoetryComment) {
        List<BizPoetryComment> list = bizPoetryCommentService.selectBizPoetryCommentList(bizPoetryComment);
        ExcelUtil<BizPoetryComment> util = new ExcelUtil<BizPoetryComment>(BizPoetryComment.class);
        util.exportExcel(response, list, "诗词评论数据");
    }

    /**
     * 获取诗词评论详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:comment:query')")
    @Anonymous
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bizPoetryCommentService.selectBizPoetryCommentById(id));
    }

    /**
     * 新增诗词评论
     */
    @PreAuthorize("@ss.hasPermi('system:comment:add')")
    @Log(title = "诗词评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPoetryComment bizPoetryComment) {
        return toAjax(bizPoetryCommentService.insertBizPoetryComment(bizPoetryComment));
    }

    /**
     * 修改诗词评论
     */
    @PreAuthorize("@ss.hasPermi('system:comment:edit')")
    @Log(title = "诗词评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPoetryComment bizPoetryComment) {
        return toAjax(bizPoetryCommentService.updateBizPoetryComment(bizPoetryComment));
    }

    /**
     * 删除诗词评论
     */
    @PreAuthorize("@ss.hasPermi('system:comment:remove')")
    @Log(title = "诗词评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizPoetryCommentService.deleteBizPoetryCommentByIds(ids));
    }
}
