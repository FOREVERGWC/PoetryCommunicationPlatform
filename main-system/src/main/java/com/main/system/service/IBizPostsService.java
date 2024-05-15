package com.main.system.service;

import java.util.List;
import com.main.system.domain.BizPosts;

/**
 * 帖子Service接口
 * 
 * @author admin
 * @date 2024-05-14
 */
public interface IBizPostsService 
{
    /**
     * 查询帖子
     * 
     * @param id 帖子主键
     * @return 帖子
     */
    public BizPosts selectBizPostsById(Long id);

    /**
     * 查询帖子列表
     * 
     * @param bizPosts 帖子
     * @return 帖子集合
     */
    public List<BizPosts> selectBizPostsList(BizPosts bizPosts);

    /**
     * 新增帖子
     * 
     * @param bizPosts 帖子
     * @return 结果
     */
    public int insertBizPosts(BizPosts bizPosts);

    /**
     * 修改帖子
     * 
     * @param bizPosts 帖子
     * @return 结果
     */
    public int updateBizPosts(BizPosts bizPosts);

    /**
     * 批量删除帖子
     * 
     * @param ids 需要删除的帖子主键集合
     * @return 结果
     */
    public int deleteBizPostsByIds(Long[] ids);

    /**
     * 删除帖子信息
     * 
     * @param id 帖子主键
     * @return 结果
     */
    public int deleteBizPostsById(Long id);
}
