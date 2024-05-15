package com.main.system.service.impl;

import com.main.common.utils.DateUtils;
import com.main.common.utils.SecurityUtils;
import com.main.system.domain.BizPosts;
import com.main.system.domain.BizPostsBrowse;
import com.main.system.mapper.BizPostsBrowseMapper;
import com.main.system.mapper.BizPostsMapper;
import com.main.system.service.IBizPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 帖子Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPostsServiceImpl implements IBizPostsService {
    @Autowired
    private BizPostsMapper bizPostsMapper;
    @Autowired
    private BizPostsBrowseMapper bizPostsBrowseMapper;

    /**
     * 查询帖子
     *
     * @param id 帖子主键
     * @return 帖子
     */
    @Override
    public BizPosts selectBizPostsById(Long id) {
        return bizPostsMapper.selectBizPostsById(id);
    }

    /**
     * 查询帖子列表
     *
     * @param bizPosts 帖子
     * @return 帖子
     */
    @Override
    public List<BizPosts> selectBizPostsList(BizPosts bizPosts) {
        return bizPostsMapper.selectBizPostsList(bizPosts);
    }

    /**
     * 新增帖子
     *
     * @param bizPosts 帖子
     * @return 结果
     */
    @Override
    public int insertBizPosts(BizPosts bizPosts) {
        bizPosts.setUserId(SecurityUtils.getUserId());
        bizPosts.setClick(0L);
        // TODO: 2024/5/15 状态换成枚举值
        bizPosts.setStatus("0");
        // TODO: 2024/5/15 逻辑删除换成枚举值
        bizPosts.setDelFlag(0);
        bizPosts.setCreateBy(SecurityUtils.getUsername());
        bizPosts.setCreateTime(DateUtils.getNowDate());
        return bizPostsMapper.insertBizPosts(bizPosts);
    }

    /**
     * 修改帖子
     *
     * @param bizPosts 帖子
     * @return 结果
     */
    @Override
    public int updateBizPosts(BizPosts bizPosts) {
        bizPosts.setUpdateBy(SecurityUtils.getUsername());
        bizPosts.setUpdateTime(DateUtils.getNowDate());
        return bizPostsMapper.updateBizPosts(bizPosts);
    }

    /**
     * 批量删除帖子
     *
     * @param ids 需要删除的帖子主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsByIds(Long[] ids) {
        return bizPostsMapper.deleteBizPostsByIds(ids);
    }

    /**
     * 删除帖子信息
     *
     * @param id 帖子主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsById(Long id) {
        return bizPostsMapper.deleteBizPostsById(id);
    }

    @Override
    @Transactional
    public BizPosts readBizPosts(Long id) {
        // TODO: 2024/5/14 乐观锁防止并发点击量异常、Redis存储点击量结束项目时同步点击量到数据库
        bizPostsMapper.readBizPosts(id);
        // 查询该帖浏览记录，若不存在则创建，否则更新
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        BizPostsBrowse history = bizPostsBrowseMapper.selectBizPostsBrowseByPostsIdAndUserId(id, userId);
        if (history == null) {
            BizPostsBrowse bizPostsBrowse = new BizPostsBrowse();
            bizPostsBrowse.setPostsId(id);
            bizPostsBrowse.setUserId(userId);
            bizPostsBrowse.setCreateBy(username);
            bizPostsBrowse.setCreateTime(DateUtils.getNowDate());
            bizPostsBrowseMapper.insertBizPostsBrowse(bizPostsBrowse);
        } else {
            history.setUpdateBy(SecurityUtils.getUsername());
            history.setUpdateTime(DateUtils.getNowDate());
            bizPostsBrowseMapper.updateBizPostsBrowse(history);
        }
        return selectBizPostsById(id);
    }
}
