package com.main.system.service.impl;

import com.main.common.utils.DateUtils;
import com.main.system.domain.BizPosts;
import com.main.system.domain.BizPostsBrowse;
import com.main.system.mapper.BizPostsBrowseMapper;
import com.main.system.mapper.BizPostsMapper;
import com.main.system.service.IBizPostsBrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子浏览记录Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPostsBrowseServiceImpl implements IBizPostsBrowseService {
    @Autowired
    private BizPostsBrowseMapper bizPostsBrowseMapper;
    @Autowired
    private BizPostsMapper bizPostsMapper;

    /**
     * 查询帖子浏览记录
     *
     * @param id 帖子浏览记录主键
     * @return 帖子浏览记录
     */
    @Override
    public BizPostsBrowse selectBizPostsBrowseById(Long id) {
        return bizPostsBrowseMapper.selectBizPostsBrowseById(id);
    }

    /**
     * 查询帖子浏览记录列表
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 帖子浏览记录
     */
    @Override
    public List<BizPostsBrowse> selectBizPostsBrowseList(BizPostsBrowse bizPostsBrowse) {
        List<BizPostsBrowse> bizPostsBrowses = bizPostsBrowseMapper.selectBizPostsBrowseList(bizPostsBrowse);
        if (bizPostsBrowses.isEmpty()) {
            return new ArrayList<>();
        }
        // 帖子信息
        List<Long> postsIds = bizPostsBrowses.stream().map(BizPostsBrowse::getPostsId).collect(Collectors.toList());
        List<BizPosts> poetryList = bizPostsMapper.selectBizPostsListByIds(postsIds);
        Map<Long, String> titleMap = poetryList.stream().collect(Collectors.toMap(BizPosts::getId, BizPosts::getTitle));
        return bizPostsBrowses.stream()
                .peek(item -> item.setPostsTitle(titleMap.get(item.getPostsId())))
                .collect(Collectors.toList());
    }

    /**
     * 新增帖子浏览记录
     *
     * @param bizPostsBrowse 帖子浏览记录
     * @return 结果
     */
    @Override
    public int insertBizPostsBrowse(BizPostsBrowse bizPostsBrowse) {
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
    public int updateBizPostsBrowse(BizPostsBrowse bizPostsBrowse) {
        return bizPostsBrowseMapper.updateBizPostsBrowse(bizPostsBrowse);
    }

    /**
     * 批量删除帖子浏览记录
     *
     * @param ids 需要删除的帖子浏览记录主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsBrowseByIds(Long[] ids) {
        return bizPostsBrowseMapper.deleteBizPostsBrowseByIds(ids);
    }

    /**
     * 删除帖子浏览记录信息
     *
     * @param id 帖子浏览记录主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsBrowseById(Long id) {
        return bizPostsBrowseMapper.deleteBizPostsBrowseById(id);
    }
}
