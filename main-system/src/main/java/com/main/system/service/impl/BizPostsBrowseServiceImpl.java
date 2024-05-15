package com.main.system.service.impl;

import java.util.List;
import com.main.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.main.system.mapper.BizPostsBrowseMapper;
import com.main.system.domain.BizPostsBrowse;
import com.main.system.service.IBizPostsBrowseService;

/**
 * 帖子浏览记录Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPostsBrowseServiceImpl implements IBizPostsBrowseService
{
    @Autowired
    private BizPostsBrowseMapper bizPostsBrowseMapper;

    /**
     * 查询帖子浏览记录
     *
     * @param id 帖子浏览记录主键
     * @return 帖子浏览记录
     */
    @Override
    public BizPostsBrowse selectBizPostsBrowseById(Long id)
    {
        return bizPostsBrowseMapper.selectBizPostsBrowseById(id);
    }

    /**
     * 查询帖子浏览记录列表
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 帖子浏览记录
     */
    @Override
    public List<BizPostsBrowse> selectBizPostsBrowseList(BizPostsBrowse bizPostsBrowse)
    {
        return bizPostsBrowseMapper.selectBizPostsBrowseList(bizPostsBrowse);
    }

    /**
     * 新增帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    @Override
    public int insertBizPostsBrowse(BizPostsBrowse bizPostsBrowse)
    {
        bizPostsBrowse.setCreateTime(DateUtils.getNowDate());
        return bizPostsBrowseMapper.insertBizPostsBrowse(bizPostsBrowse);
    }

    /**
     * 修改帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    @Override
    public int updateBizPostsBrowse(BizPostsBrowse bizPostsBrowse)
    {
        return bizPostsBrowseMapper.updateBizPostsBrowse(bizPostsBrowse);
    }

    /**
     * 批量删除帖子浏览记录
     *
     * @param ids 需要删除的帖子浏览记录主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsBrowseByIds(Long[] ids)
    {
        return bizPostsBrowseMapper.deleteBizPostsBrowseByIds(ids);
    }

    /**
     * 删除帖子浏览记录信息
     *
     * @param id 帖子浏览记录主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsBrowseById(Long id)
    {
        return bizPostsBrowseMapper.deleteBizPostsBrowseById(id);
    }
}
