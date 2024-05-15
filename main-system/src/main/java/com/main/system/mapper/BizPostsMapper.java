package com.main.system.mapper;

import com.main.system.domain.BizPosts;

import java.util.List;

/**
 * 帖子Mapper接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface BizPostsMapper {
    /**
     * 查询帖子
     *
     * @param id 帖子主键
     * @return 帖子
     */
    BizPosts selectBizPostsById(Long id);

    /**
     * 查询帖子列表
     *
     * @param bizPosts 帖子
     * @return 帖子集合
     */
    List<BizPosts> selectBizPostsList(BizPosts bizPosts);

    /**
     * 新增帖子
     *
     * @param bizPosts 帖子
     * @return 结果
     */
    int insertBizPosts(BizPosts bizPosts);

    /**
     * 修改帖子
     *
     * @param bizPosts 帖子
     * @return 结果
     */
    int updateBizPosts(BizPosts bizPosts);

    /**
     * 删除帖子
     *
     * @param id 帖子主键
     * @return 结果
     */
    int deleteBizPostsById(Long id);

    /**
     * 批量删除帖子
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBizPostsByIds(Long[] ids);

    /**
     * 阅读帖子
     *
     * @param id 帖子ID
     * @return 结果
     */
    int readBizPosts(Long id);

    /**
     * 根据帖子ID批量查询诗词
     *
     * @param ids 帖子ID列表
     * @return 结果
     */
    List<BizPosts> selectBizPostsListByIds(List<Long> ids);
}
