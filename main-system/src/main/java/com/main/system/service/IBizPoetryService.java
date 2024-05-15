package com.main.system.service;

import com.main.system.domain.BizPoetry;

import java.util.List;

/**
 * 诗词Service接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface IBizPoetryService {
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
     * 批量删除诗词
     *
     * @param ids 需要删除的诗词主键集合
     * @return 结果
     */
    int deleteBizPoetryByIds(Long[] ids);

    /**
     * 删除诗词信息
     *
     * @param id 诗词主键
     * @return 结果
     */
    int deleteBizPoetryById(Long id);

    /**
     * 阅读诗词
     *
     * @param id 诗词主键
     * @return 结果
     */
    BizPoetry readBizPoetry(Long id);
}
