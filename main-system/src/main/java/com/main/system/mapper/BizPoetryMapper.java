package com.main.system.mapper;

import com.main.system.domain.BizPoetry;
import com.main.system.domain.BizPoetryComment;

import java.util.List;

/**
 * 诗词Mapper接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface BizPoetryMapper {
    /**
     * 查询诗词
     *
     * @param id 诗词主键
     * @return 诗词
     */
    BizPoetry selectBizPoetryById(Long id);

    /**
     * 查询诗词列表
     *
     * @param bizPoetry 诗词
     * @return 诗词集合
     */
    List<BizPoetry> selectBizPoetryList(BizPoetry bizPoetry);

    /**
     * 新增诗词
     *
     * @param bizPoetry 诗词
     * @return 结果
     */
    int insertBizPoetry(BizPoetry bizPoetry);

    /**
     * 修改诗词
     *
     * @param bizPoetry 诗词
     * @return 结果
     */
    int updateBizPoetry(BizPoetry bizPoetry);

    /**
     * 删除诗词
     *
     * @param id 诗词主键
     * @return 结果
     */
    int deleteBizPoetryById(Long id);

    /**
     * 批量删除诗词
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBizPoetryByIds(Long[] ids);

    /**
     * 批量删除诗词评论
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBizPoetryCommentByPoetryIds(Long[] ids);

    /**
     * 批量新增诗词评论
     *
     * @param bizPoetryCommentList 诗词评论列表
     * @return 结果
     */
    int batchBizPoetryComment(List<BizPoetryComment> bizPoetryCommentList);


    /**
     * 通过诗词主键删除诗词评论信息
     *
     * @param id 诗词ID
     * @return 结果
     */
    int deleteBizPoetryCommentByPoetryId(Long id);

    /**
     * 阅读诗词
     *
     * @param id 诗词ID
     * @return 结果
     */
    int readBizPoetry(Long id);

    /**
     * 根据诗词ID批量查询诗词
     *
     * @param ids 诗词ID列表
     * @return 结果
     */
    List<BizPoetry> selectBizPoetryListByIds(List<Long> ids);
}
