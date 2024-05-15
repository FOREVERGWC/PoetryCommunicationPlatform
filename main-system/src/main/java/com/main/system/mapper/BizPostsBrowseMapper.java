package com.main.system.mapper;

import com.main.system.domain.BizPostsBrowse;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 帖子浏览记录Mapper接口
 *
 * @author admin
 * @date 2024-05-14
 */
public interface BizPostsBrowseMapper {
    /**
     * 查询帖子浏览记录
     *
     * @param id 帖子浏览记录主键
     * @return 帖子浏览记录
     */
    public BizPostsBrowse selectBizPostsBrowseById(Long id);

    /**
     * 查询帖子浏览记录列表
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 帖子浏览记录集合
     */
    public List<BizPostsBrowse> selectBizPostsBrowseList(BizPostsBrowse bizPostsBrowse);

    /**
     * 新增帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    public int insertBizPostsBrowse(BizPostsBrowse bizPostsBrowse);

    /**
     * 修改帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    public int updateBizPostsBrowse(BizPostsBrowse bizPostsBrowse);

    /**
     * 删除帖子浏览记录
     *
     * @param id 帖子浏览记录主键
     * @return 结果
     */
    public int deleteBizPostsBrowseById(Long id);

    /**
     * 批量删除帖子浏览记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizPostsBrowseByIds(Long[] ids);

    /**
     * 根据帖子ID和用户ID查询浏览记录
     *
     * @param postsId 帖子ID
     * @param userId  用户ID
     * @return 结果
     */
    BizPostsBrowse selectBizPostsBrowseByPostsIdAndUserId(@Param("postsId") Long postsId, @Param("userId") Long userId);
}
