package com.main.system.mapper;

import com.main.system.domain.BizPostsReply;

import java.util.List;

/**
 * 帖子回复Mapper接口
 *
 * @author admin
 * @date 2024-05-16
 */
public interface BizPostsReplyMapper {
    /**
     * 查询帖子回复
     *
     * @param id 帖子回复主键
     * @return 帖子回复
     */
    BizPostsReply selectBizPostsReplyById(Long id);

    /**
     * 查询帖子回复列表
     *
     * @param bizPostsReply 帖子回复
     * @return 帖子回复集合
     */
    List<BizPostsReply> selectBizPostsReplyList(BizPostsReply bizPostsReply);

    /**
     * 新增帖子回复
     *
     * @param bizPostsReply 帖子回复
     * @return 结果
     */
    int insertBizPostsReply(BizPostsReply bizPostsReply);

    /**
     * 修改帖子回复
     *
     * @param bizPostsReply 帖子回复
     * @return 结果
     */
    int updateBizPostsReply(BizPostsReply bizPostsReply);

    /**
     * 删除帖子回复
     *
     * @param id 帖子回复主键
     * @return 结果
     */
    int deleteBizPostsReplyById(Long id);

    /**
     * 批量删除帖子回复
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBizPostsReplyByIds(Long[] ids);
}
