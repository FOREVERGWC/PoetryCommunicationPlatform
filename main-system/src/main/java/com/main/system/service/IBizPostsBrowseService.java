package com.main.system.service;

import com.main.system.domain.BizPostsBrowse;

import java.util.List;

/**
 * 帖子浏览记录Service接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface IBizPostsBrowseService {
    /**
     * 查询帖子浏览记录
     *
     * @param id 帖子浏览记录主键
     * @return 帖子浏览记录
     */
    BizPostsBrowse selectBizPostsBrowseById(Long id);

    /**
     * 查询帖子浏览记录列表
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 帖子浏览记录集合
     */
    List<BizPostsBrowse> selectBizPostsBrowseList(BizPostsBrowse bizPostsBrowse);

    /**
     * 新增帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    int insertBizPostsBrowse(BizPostsBrowse bizPostsBrowse);

    /**
     * 修改帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    int updateBizPostsBrowse(BizPostsBrowse bizPostsBrowse);

    /**
     * 批量删除帖子浏览记录
     *
     * @param ids 需要删除的帖子浏览记录主键集合
     * @return 结果
     */
    int deleteBizPostsBrowseByIds(Long[] ids);

    /**
     * 删除帖子浏览记录信息
     *
     * @param id 帖子浏览记录主键
     * @return 结果
     */
    int deleteBizPostsBrowseById(Long id);
}
