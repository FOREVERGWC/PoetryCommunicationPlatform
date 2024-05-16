package com.main.system.service.impl;

import com.main.common.core.domain.entity.SysDictData;
import com.main.common.utils.DateUtils;
import com.main.common.utils.SecurityUtils;
import com.main.common.utils.StringUtils;
import com.main.system.domain.BizPoetry;
import com.main.system.domain.BizPoetryComment;
import com.main.system.mapper.BizPoetryMapper;
import com.main.system.service.IBizPoetryCommentService;
import com.main.system.service.IBizPoetryService;
import com.main.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 诗词Service业务层处理
 *
 * @author admin
 * @date 2024-05-14
 */
@Service
public class BizPoetryServiceImpl implements IBizPoetryService {
    @Autowired
    private BizPoetryMapper bizPoetryMapper;
    @Autowired
    private IBizPoetryCommentService bizPoetryCommentService;
    @Resource
    private ISysDictDataService sysDictDataService;

    /**
     * 查询诗词
     *
     * @param id 诗词主键
     * @return 诗词
     */
    @Override
    public BizPoetry selectBizPoetryById(Long id) {
        BizPoetry bizPoetry = bizPoetryMapper.selectBizPoetryById(id);
        BizPoetryComment bizPoetryComment = new BizPoetryComment();
        bizPoetryComment.setPoetryId(id);
        // 字典
        String dictLabel = sysDictDataService.selectDictLabel("biz_poetry_dynasty", bizPoetry.getDynasty());
        bizPoetry.setDynastyText(dictLabel);
        // 评论
        List<BizPoetryComment> bizPoetryCommentList = bizPoetryCommentService.selectBizPoetryCommentList(bizPoetryComment);
        bizPoetry.setBizPoetryCommentList(bizPoetryCommentList);
        bizPoetry.setImgList();
        return bizPoetry;
    }

    /**
     * 查询诗词列表
     *
     * @param bizPoetry 诗词
     * @return 诗词
     */
    @Override
    public List<BizPoetry> selectBizPoetryList(BizPoetry bizPoetry) {
        List<BizPoetry> bizPoetryList = bizPoetryMapper.selectBizPoetryList(bizPoetry);
        if (bizPoetryList.isEmpty()) {
            return new ArrayList<>();
        }
        // 字典
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("biz_poetry_dynasty");
        List<SysDictData> dictData = sysDictDataService.selectDictDataList(sysDictData);
        Map<String, String> dictDataMap = dictData.stream().collect(Collectors.toMap(SysDictData::getDictValue, SysDictData::getDictLabel));
        // 评论
        List<Long> ids = bizPoetryList.stream().map(BizPoetry::getId).collect(Collectors.toList());
        List<BizPoetryComment> bizPoetryCommentList = bizPoetryCommentService.selectBizPoetryCommentListByPoetryIds(ids);
        Map<Long, List<BizPoetryComment>> bizPoetryCommentMap = bizPoetryCommentList.stream().collect(Collectors.groupingBy(BizPoetryComment::getPoetryId));
        bizPoetryList.forEach(item -> {
            item.setImgList();
            item.setDynastyText(dictDataMap.get(item.getDynasty()));
            item.setBizPoetryCommentList(bizPoetryCommentMap.get(item.getId()));
        });
        return bizPoetryList;
    }

    /**
     * 新增诗词
     *
     * @param bizPoetry 诗词
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBizPoetry(BizPoetry bizPoetry) {
        bizPoetry.setClick(0L);
        bizPoetry.setCreateBy(SecurityUtils.getUsername());
        bizPoetry.setCreateTime(DateUtils.getNowDate());
        int rows = bizPoetryMapper.insertBizPoetry(bizPoetry);
        insertBizPoetryComment(bizPoetry);
        return rows;
    }

    /**
     * 修改诗词
     *
     * @param bizPoetry 诗词
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBizPoetry(BizPoetry bizPoetry) {
        bizPoetry.setUpdateTime(DateUtils.getNowDate());
        bizPoetryMapper.deleteBizPoetryCommentByPoetryId(bizPoetry.getId());
        insertBizPoetryComment(bizPoetry);
        return bizPoetryMapper.updateBizPoetry(bizPoetry);
    }

    /**
     * 批量删除诗词
     *
     * @param ids 需要删除的诗词主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBizPoetryByIds(Long[] ids) {
        bizPoetryMapper.deleteBizPoetryCommentByPoetryIds(ids);
        return bizPoetryMapper.deleteBizPoetryByIds(ids);
    }

    /**
     * 删除诗词信息
     *
     * @param id 诗词主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBizPoetryById(Long id) {
        bizPoetryMapper.deleteBizPoetryCommentByPoetryId(id);
        return bizPoetryMapper.deleteBizPoetryById(id);
    }

    @Override
    public BizPoetry readBizPoetry(Long id) {
        // TODO: 2024/5/14 乐观锁防止并发点击量异常、Redis存储点击量结束项目时同步点击量到数据库
        // TODO: 2024/5/14 添加阅读记录
        bizPoetryMapper.readBizPoetry(id);
        return selectBizPoetryById(id);
    }

    /**
     * 新增诗词评论信息
     *
     * @param bizPoetry 诗词对象
     */
    public void insertBizPoetryComment(BizPoetry bizPoetry) {
        List<BizPoetryComment> bizPoetryCommentList = bizPoetry.getBizPoetryCommentList();
        Long id = bizPoetry.getId();
        if (StringUtils.isNotNull(bizPoetryCommentList)) {
            List<BizPoetryComment> list = new ArrayList<>();
            for (BizPoetryComment bizPoetryComment : bizPoetryCommentList) {
                bizPoetryComment.setPoetryId(id);
                list.add(bizPoetryComment);
            }
            if (list.size() > 0) {
                bizPoetryMapper.batchBizPoetryComment(list);
            }
        }
    }
}
