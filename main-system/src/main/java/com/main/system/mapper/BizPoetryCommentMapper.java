package com.main.system.mapper;

import com.main.system.domain.BizPoetryComment;

import java.util.List;

/**
 * 诗词评论Mapper接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface BizPoetryCommentMapper {
    /**
     * 查询诗词评论
     *
     * @param id 诗词评论主键
     * @return 诗词评论
     */
    BizPoetryComment selectBizPoetryCommentById(Long id);

    /**
     * 查询诗词评论列表
     *
     * @param bizPoetryComment 诗词评论
     * @return 诗词评论集合
     */
    List<BizPoetryComment> selectBizPoetryCommentList(BizPoetryComment bizPoetryComment);

    /**
     * 新增诗词评论
     *
     * @param bizPoetryComment 诗词评论
     * @return 结果
     */
    int insertBizPoetryComment(BizPoetryComment bizPoetryComment);

    /**
     * 修改诗词评论
     *
     * @param bizPoetryComment 诗词评论
     * @return 结果
     */
    int updateBizPoetryComment(BizPoetryComment bizPoetryComment);

    /**
     * 删除诗词评论
     *
     * @param id 诗词评论主键
     * @return 结果
     */
    int deleteBizPoetryCommentById(Long id);

    /**
     * 批量删除诗词评论
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBizPoetryCommentByIds(Long[] ids);
}
