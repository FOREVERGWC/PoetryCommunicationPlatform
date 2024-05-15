package com.main.system.service;

import com.main.system.domain.BizPoetryFavorite;

import java.util.List;

/**
 * 用户诗词收藏Service接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface IBizPoetryFavoriteService {
    /**
     * 查询用户诗词收藏
     *
     * @param id 用户诗词收藏主键
     * @return 用户诗词收藏
     */
    BizPoetryFavorite selectBizPoetryFavoriteById(Long id);

    /**
     * 查询用户诗词收藏列表
     *
     * @param bizPoetryFavorite 用户诗词收藏
     * @return 用户诗词收藏集合
     */
    List<BizPoetryFavorite> selectBizPoetryFavoriteList(BizPoetryFavorite bizPoetryFavorite);

    /**
     * 新增用户诗词收藏
     *
     * @param bizPoetryFavorite 用户诗词收藏
     * @return 结果
     */
    int insertBizPoetryFavorite(BizPoetryFavorite bizPoetryFavorite);

    /**
     * 修改用户诗词收藏
     *
     * @param bizPoetryFavorite 用户诗词收藏
     * @return 结果
     */
    int updateBizPoetryFavorite(BizPoetryFavorite bizPoetryFavorite);

    /**
     * 批量删除用户诗词收藏
     *
     * @param ids 需要删除的用户诗词收藏主键集合
     * @return 结果
     */
    int deleteBizPoetryFavoriteByIds(Long[] ids);

    /**
     * 删除用户诗词收藏信息
     *
     * @param id 用户诗词收藏主键
     * @return 结果
     */
    int deleteBizPoetryFavoriteById(Long id);

    /**
     * 获取用户诗词收藏详细信息
     *
     * @param poetryId 诗词ID
     * @return 结果
     */
    BizPoetryFavorite selectBizPoetryFavoriteByPoetryId(Long poetryId);

    /**
     * 用户诗词收藏、取消
     *
     * @param poetryId 诗词ID
     * @return 结果
     */
    BizPoetryFavorite favorite(Long poetryId);
}
