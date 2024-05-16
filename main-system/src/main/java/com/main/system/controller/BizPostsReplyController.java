package com.main.system.controller;

import com.main.common.annotation.Log;
import com.main.common.core.controller.BaseController;
import com.main.common.core.domain.AjaxResult;
import com.main.common.core.page.TableDataInfo;
import com.main.common.enums.BusinessType;
import com.main.common.utils.poi.ExcelUtil;
import com.main.system.domain.BizPostsReply;
import com.main.system.service.IBizPostsReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子回复Controller
 *
 * @author admin
 * @date 2024-05-16
 */
@RestController
@RequestMapping("/system/reply")
public class BizPostsReplyController extends BaseController {
    @Autowired
    private IBizPostsReplyService bizPostsReplyService;

    private static BizPostsReply findTopParent(BizPostsReply reply, Map<Long, BizPostsReply> nodeMap) {
        Long parentId = reply.getReplyId();
        if (parentId == null) {
            return reply;
        }
        BizPostsReply parentReply = nodeMap.get(parentId);
        return findTopParent(parentReply, nodeMap);
    }

    /**
     * 查询帖子回复列表
     */
    @PreAuthorize("@ss.hasPermi('system:reply:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPostsReply bizPostsReply) {
        startPage();
        List<BizPostsReply> list = bizPostsReplyService.selectBizPostsReplyList(bizPostsReply);
        // 构造不完全的树
        List<BizPostsReply> tree = buildTree(list);
//        List<BizPostsReply> tree = DataUtils.listToTree(list, BizPostsReply::getReplyId, BizPostsReply::setChildren, BizPostsReply::getId, null);
        return getDataTable(tree);
    }

    private List<BizPostsReply> buildTree(List<BizPostsReply> list) {
        List<BizPostsReply> bizPostsReplyList = new ArrayList<>();
        Map<Long, BizPostsReply> map = list.stream().collect(Collectors.toMap(BizPostsReply::getId, item -> item));
        for (BizPostsReply item : list) {
            Long replyId = item.getReplyId();
            if (replyId == null) {
                bizPostsReplyList.add(item);
            }
        }
        // 寻找顶级节点
        for (BizPostsReply item : list) {
            List<BizPostsReply> specifiedItems = bizPostsReplyList.stream()
                    .filter(reply -> reply.getId().equals(item.getId()))
                    .collect(Collectors.toList());
            if (!specifiedItems.isEmpty()) {
                continue;
            }
            BizPostsReply topParent = findTopParent(item, map);
            Long topParentId = topParent.getId();
            BizPostsReply bizPostsReply = bizPostsReplyList.stream()
                    .filter(reply -> reply.getId().equals(topParentId))
                    .findFirst().get();
            List<BizPostsReply> children = (List<BizPostsReply>) bizPostsReply.getChildren();
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(item);
            bizPostsReply.setChildren(children);
        }
        return bizPostsReplyList;
    }

    /**
     * 导出帖子回复列表
     */
    @PreAuthorize("@ss.hasPermi('system:reply:export')")
    @Log(title = "帖子回复", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPostsReply bizPostsReply) {
        List<BizPostsReply> list = bizPostsReplyService.selectBizPostsReplyList(bizPostsReply);
        ExcelUtil<BizPostsReply> util = new ExcelUtil<BizPostsReply>(BizPostsReply.class);
        util.exportExcel(response, list, "帖子回复数据");
    }

    /**
     * 获取帖子回复详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:reply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bizPostsReplyService.selectBizPostsReplyById(id));
    }

    /**
     * 新增帖子回复
     */
    @PreAuthorize("@ss.hasPermi('system:reply:add')")
    @Log(title = "帖子回复", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPostsReply bizPostsReply) {
        return toAjax(bizPostsReplyService.insertBizPostsReply(bizPostsReply));
    }

    /**
     * 修改帖子回复
     */
    @PreAuthorize("@ss.hasPermi('system:reply:edit')")
    @Log(title = "帖子回复", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPostsReply bizPostsReply) {
        return toAjax(bizPostsReplyService.updateBizPostsReply(bizPostsReply));
    }

    /**
     * 删除帖子回复
     */
    @PreAuthorize("@ss.hasPermi('system:reply:remove')")
    @Log(title = "帖子回复", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bizPostsReplyService.deleteBizPostsReplyByIds(ids));
    }
}
