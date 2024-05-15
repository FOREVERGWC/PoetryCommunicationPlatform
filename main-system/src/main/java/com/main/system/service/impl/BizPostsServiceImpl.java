package com.main.system.service.impl;

import java.util.List;
import com.main.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.main.system.mapper.BizPostsMapper;
import com.main.system.domain.BizPosts;
import com.main.system.service.IBizPostsService;

/**
 * 帖子Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPostsServiceImpl implements IBizPostsService
{
    @Autowired
    private BizPostsMapper bizPostsMapper;

    /**
     * 查询帖子
     *
     * @param id 帖子主键
     * @return 帖子
     */
    @Override
    public BizPosts selectBizPostsById(Long id)
    {
        return bizPostsMapper.selectBizPostsById(id);
    }

    /**
     * 查询帖子列表
     *
     * @param bizPosts 帖子
     * @return 帖子
     */
    @Override
    public List<BizPosts> selectBizPostsList(BizPosts bizPosts)
    {
        return bizPostsMapper.selectBizPostsList(bizPosts);
    }

    /**
     * 新增帖子
     *
     * @param bizPosts 帖子
     * @return 结果
     */
    @Override
    public int insertBizPosts(BizPosts bizPosts)
    {
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
    public int updateBizPosts(BizPosts bizPosts)
    {
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
    public int deleteBizPostsByIds(Long[] ids)
    {
        return bizPostsMapper.deleteBizPostsByIds(ids);
    }

    /**
     * 删除帖子信息
     *
     * @param id 帖子主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsById(Long id)
    {
        return bizPostsMapper.deleteBizPostsById(id);
    }
}
