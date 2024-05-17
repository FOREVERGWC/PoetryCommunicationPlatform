package com.main.system.controller;

import com.main.common.annotation.Anonymous;
import com.main.common.annotation.Log;
import com.main.common.core.controller.BaseController;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.page.TableDataInfo;
import com.main.common.enums.BusinessType;
import com.main.common.utils.poi.ExcelUtil;
import com.main.system.domain.BizPosts;
import com.main.system.service.IBizPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 帖子Controller
 *
 * @author admin
 * @date 2024-05-14
 */
@RestController
@RequestMapping("/system/posts")
public class BizPostsController extends BaseController {
    @Autowired
    private IBizPostsService bizPostsService;

    /**
     * 查询帖子列表
     */
    @PreAuthorize("@ss.hasPermi('system:posts:list')")
    @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(BizPosts bizPosts) {
        startPage();
        List<BizPosts> list = bizPostsService.selectBizPostsList(bizPosts);
        return getDataTable(list);
    }

    /**
     * 导出帖子列表
     */
    @PreAuthorize("@ss.hasPermi('system:posts:export')")
    @Log(title = "帖子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPosts bizPosts) {
        List<BizPosts> list = bizPostsService.selectBizPostsList(bizPosts);
        ExcelUtil<BizPosts> util = new ExcelUtil<BizPosts>(BizPosts.class);
        util.exportExcel(response, list, "帖子数据");
    }

    /**
     * 获取帖子详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:posts:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bizPostsService.selectBizPostsById(id));
    }

    /**
     * 新增帖子
     */
    @PreAuthorize("@ss.hasPermi('system:posts:add')")
    @Log(title = "帖子", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPosts bizPosts) {
        return toAjax(bizPostsService.insertBizPosts(bizPosts));
    }

    /**
     * 修改帖子
     */
    @PreAuthorize("@ss.hasPermi('system:posts:edit')")
    @Log(title = "帖子", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPosts bizPosts) {
        return toAjax(bizPostsService.updateBizPosts(bizPosts));
    }

    /**
     * 阅读帖子
     */
    @PreAuthorize("@ss.hasPermi('system:posts:query')")
    @Log(title = "帖子", businessType = BusinessType.UPDATE)
    @PutMapping("/read/{id}")
    public AjaxResult read(@PathVariable Long id) {
        return success(bizPostsService.readBizPosts(id));
    }

    /**
     * 删除帖子
     */
    @PreAuthorize("@ss.hasPermi('system:posts:remove')")
    @Log(title = "帖子", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizPostsService.deleteBizPostsByIds(ids));
    }
}
