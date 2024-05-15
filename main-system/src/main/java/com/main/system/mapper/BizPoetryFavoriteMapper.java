package com.main.system.mapper;

import com.main.system.domain.BizPoetryFavorite;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户诗词收藏Mapper接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface BizPoetryFavoriteMapper {
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
     * 删除用户诗词收藏
     *
     * @param id 用户诗词收藏主键
     * @return 结果
     */
    int deleteBizPoetryFavoriteById(Long id);

    /**
     * 批量删除用户诗词收藏
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteBizPoetryFavoriteByIds(Long[] ids);

    BizPoetryFavorite selectBizPoetryFavoriteByPoetryIdAndUserId(@Param("userId") Long userId, @Param("poetryId") Long poetryId);
}
