package com.main.system.service.impl;

import com.main.common.core.domain.entity.SysUser;
import com.main.common.utils.DateUtils;
import com.main.common.utils.SecurityUtils;
import com.main.system.domain.BizPoetry;
import com.main.system.domain.BizPoetryComment;
import com.main.system.domain.vo.SysUserVo;
import com.main.system.mapper.BizPoetryCommentMapper;
import com.main.system.mapper.BizPoetryMapper;
import com.main.system.mapper.SysUserMapper;
import com.main.system.service.IBizPoetryCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 诗词评论Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPoetryCommentServiceImpl implements IBizPoetryCommentService {
    @Autowired
    private BizPoetryCommentMapper bizPoetryCommentMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private BizPoetryMapper bizPoetryMapper;

    /**
     * 查询诗词评论
     *
     * @param id 诗词评论主键
     * @return 诗词评论
     */
    @Override
    public BizPoetryComment selectBizPoetryCommentById(Long id) {
        return bizPoetryCommentMapper.selectBizPoetryCommentById(id);
    }

    /**
     * 查询诗词评论列表
     *
     * @param bizPoetryComment 诗词评论
     * @return 诗词评论
     */
    @Override
    public List<BizPoetryComment> selectBizPoetryCommentList(BizPoetryComment bizPoetryComment) {
        List<BizPoetryComment> bizPoetryCommentList = bizPoetryCommentMapper.selectBizPoetryCommentList(bizPoetryComment);
        if (bizPoetryCommentList.isEmpty()) {
            return new ArrayList<>();
        }
        // 用户信息
        List<Long> userIds = bizPoetryCommentList.stream().map(BizPoetryComment::getUserId).collect(Collectors.toList());
        List<SysUser> userList = sysUserMapper.selectUsersByUserIds(userIds);
        Map<Long, SysUserVo> voMap = userList.stream()
                .map(item -> {
                    SysUserVo vo = new SysUserVo();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                })
                .collect(Collectors.toMap(SysUserVo::getUserId, item -> item));
        // 诗词信息
        List<Long> poetryIds = bizPoetryCommentList.stream().map(BizPoetryComment::getPoetryId).collect(Collectors.toList());
        List<BizPoetry> poetryList = bizPoetryMapper.selectBizPoetryListByIds(poetryIds);
        Map<Long, String> titleMap = poetryList.stream().collect(Collectors.toMap(BizPoetry::getId, BizPoetry::getTitle));
        return bizPoetryCommentList.stream()
                .peek(item -> {
                    item.setPoetryTitle(titleMap.get(item.getPoetryId()));
                    item.setUser(voMap.get(item.getUserId()));
                })
                .collect(Collectors.toList());
    }

    /**
     * 新增诗词评论
     *
     * @param bizPoetryComment 诗词评论
     * @return 结果
     */
    @Override
    public int insertBizPoetryComment(BizPoetryComment bizPoetryComment) {
        bizPoetryComment.setUserId(SecurityUtils.getUserId());
        bizPoetryComment.setFavor(0L);
        bizPoetryComment.setCreateBy(SecurityUtils.getUsername());
        bizPoetryComment.setCreateTime(DateUtils.getNowDate());
        return bizPoetryCommentMapper.insertBizPoetryComment(bizPoetryComment);
    }

    /**
     * 修改诗词评论
     *
     * @param bizPoetryComment 诗词评论
     * @return 结果
     */
    @Override
    public int updateBizPoetryComment(BizPoetryComment bizPoetryComment) {
        bizPoetryComment.setUpdateBy(SecurityUtils.getUsername());
        bizPoetryComment.setUpdateTime(DateUtils.getNowDate());
        return bizPoetryCommentMapper.updateBizPoetryComment(bizPoetryComment);
    }

    /**
     * 批量删除诗词评论
     *
     * @param ids 需要删除的诗词评论主键
     * @return 结果
     */
    @Override
    public int deleteBizPoetryCommentByIds(Long[] ids) {
        return bizPoetryCommentMapper.deleteBizPoetryCommentByIds(ids);
    }

    /**
     * 删除诗词评论信息
     *
     * @param id 诗词评论主键
     * @return 结果
     */
    @Override
    public int deleteBizPoetryCommentById(Long id) {
        return bizPoetryCommentMapper.deleteBizPoetryCommentById(id);
    }
}
