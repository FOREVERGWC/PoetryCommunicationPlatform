package com.main.system.service.impl;

import com.main.common.utils.DateUtils;
import com.main.common.utils.SecurityUtils;
import com.main.system.domain.BizPoetry;
import com.main.system.domain.BizPoetryFavorite;
import com.main.system.mapper.BizPoetryFavoriteMapper;
import com.main.system.mapper.BizPoetryMapper;
import com.main.system.service.IBizPoetryFavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户诗词收藏Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPoetryFavoriteServiceImpl implements IBizPoetryFavoriteService {
    @Autowired
    private BizPoetryFavoriteMapper bizPoetryFavoriteMapper;
    @Autowired
    private BizPoetryMapper bizPoetryMapper;

    /**
     * 查询用户诗词收藏
     *
     * @param id 用户诗词收藏主键
     * @return 用户诗词收藏
     */
    @Override
    public BizPoetryFavorite selectBizPoetryFavoriteById(Long id) {
        return bizPoetryFavoriteMapper.selectBizPoetryFavoriteById(id);
    }

    /**
     * 查询用户诗词收藏列表
     *
     * @param bizPoetryFavorite 用户诗词收藏
     * @return 用户诗词收藏
     */
    @Override
    public List<BizPoetryFavorite> selectBizPoetryFavoriteList(BizPoetryFavorite bizPoetryFavorite) {
        List<BizPoetryFavorite> bizPoetryFavorites = bizPoetryFavoriteMapper.selectBizPoetryFavoriteList(bizPoetryFavorite);
        if (bizPoetryFavorites.isEmpty()) {
            return new ArrayList<>();
        }
        // 诗词信息
        List<Long> poetryIds = bizPoetryFavorites.stream().map(BizPoetryFavorite::getPoetryId).collect(Collectors.toList());
        List<BizPoetry> poetryList = bizPoetryMapper.selectBizPoetryListByIds(poetryIds);
        Map<Long, String> titleMap = poetryList.stream().collect(Collectors.toMap(BizPoetry::getId, BizPoetry::getTitle));
        return bizPoetryFavorites.stream()
                .peek(item -> item.setPoetryTitle(titleMap.get(item.getPoetryId())))
                .collect(Collectors.toList());
    }

    /**
     * 新增用户诗词收藏
     *
     * @param bizPoetryFavorite 用户诗词收藏
     * @return 结果
     */
    @Override
    public int insertBizPoetryFavorite(BizPoetryFavorite bizPoetryFavorite) {
        bizPoetryFavorite.setUserId(SecurityUtils.getUserId());
        bizPoetryFavorite.setCreateBy(SecurityUtils.getUsername());
        bizPoetryFavorite.setCreateTime(DateUtils.getNowDate());
        return bizPoetryFavoriteMapper.insertBizPoetryFavorite(bizPoetryFavorite);
    }

    /**
     * 修改用户诗词收藏
     *
     * @param bizPoetryFavorite 用户诗词收藏
     * @return 结果
     */
    @Override
    public int updateBizPoetryFavorite(BizPoetryFavorite bizPoetryFavorite) {
        return bizPoetryFavoriteMapper.updateBizPoetryFavorite(bizPoetryFavorite);
    }

    /**
     * 批量删除用户诗词收藏
     *
     * @param ids 需要删除的用户诗词收藏主键
     * @return 结果
     */
    @Override
    public int deleteBizPoetryFavoriteByIds(Long[] ids) {
        return bizPoetryFavoriteMapper.deleteBizPoetryFavoriteByIds(ids);
    }

    /**
     * 删除用户诗词收藏信息
     *
     * @param id 用户诗词收藏主键
     * @return 结果
     */
    @Override
    public int deleteBizPoetryFavoriteById(Long id) {
        return bizPoetryFavoriteMapper.deleteBizPoetryFavoriteById(id);
    }

    @Override
    public BizPoetryFavorite selectBizPoetryFavoriteByPoetryId(Long poetryId) {
        Long userId = SecurityUtils.getUserId();
        if (userId == null) {
            return null;
        }
        return bizPoetryFavoriteMapper.selectBizPoetryFavoriteByPoetryIdAndUserId(userId, poetryId);
    }

    @Override
    public int countByPoetryId(Long poetryId) {
        return bizPoetryMapper.countByPoetryId(poetryId);
    }

    @Override
    @Transactional
    public BizPoetryFavorite favorite(Long poetryId) {
        // 判断是否存在记录
        BizPoetryFavorite bizPoetryFavorite = selectBizPoetryFavoriteByPoetryId(poetryId);
        // 存在则删除
        if (bizPoetryFavorite != null) {
            deleteBizPoetryFavoriteById(bizPoetryFavorite.getId());
            return null;
        }
        // 不存在则创建
        bizPoetryFavorite = new BizPoetryFavorite();
        bizPoetryFavorite.setPoetryId(poetryId);
        insertBizPoetryFavorite(bizPoetryFavorite);
        return selectBizPoetryFavoriteByPoetryId(poetryId);
    }
}
