package com.main.system.service.impl;

import com.main.common.core.domain.entity.SysUser;
import com.main.common.utils.DateUtils;
import com.main.common.utils.SecurityUtils;
import com.main.common.utils.ServletUtils;
import com.main.common.utils.ip.AddressUtils;
import com.main.common.utils.ip.IpUtils;
import com.main.system.domain.BizPoetryComment;
import com.main.system.domain.BizPostsReply;
import com.main.system.domain.vo.SysUserVo;
import com.main.system.mapper.BizPostsReplyMapper;
import com.main.system.mapper.SysUserMapper;
import com.main.system.service.IBizPostsReplyService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子回复Service业务层处理
 *
 * @author admin
 * @date 2024-05-16
 */
@Service
public class BizPostsReplyServiceImpl implements IBizPostsReplyService {
    @Autowired
    private BizPostsReplyMapper bizPostsReplyMapper;
    @Resource
    private SysUserMapper sysUserMapper;

    /**
     * 查询帖子回复
     *
     * @param id 帖子回复主键
     * @return 帖子回复
     */
    @Override
    public BizPostsReply selectBizPostsReplyById(Long id) {
        return bizPostsReplyMapper.selectBizPostsReplyById(id);
    }

    /**
     * 查询帖子回复列表
     *
     * @param bizPostsReply 帖子回复
     * @return 帖子回复
     */
    @Override
    public List<BizPostsReply> selectBizPostsReplyList(BizPostsReply bizPostsReply) {
        List<BizPostsReply> bizPostsReplies = bizPostsReplyMapper.selectBizPostsReplyList(bizPostsReply);
        if (bizPostsReplies.isEmpty()) {
            return new ArrayList<>();
        }
        // 用户信息
        List<Long> userIds = bizPostsReplies.stream().map(BizPostsReply::getUserId).collect(Collectors.toList());
        List<SysUser> userList = sysUserMapper.selectUsersByUserIds(userIds);
        Map<Long, SysUserVo> voMap = userList.stream()
                .map(item -> {
                    SysUserVo vo = new SysUserVo();
                    BeanUtils.copyProperties(item, vo);
                    return vo;
                })
                .collect(Collectors.toMap(SysUserVo::getUserId, item -> item));
        return bizPostsReplies.stream()
                .peek(item -> {
//                    item.setPoetryTitle(titleMap.get(item.getPoetryId()));
                    item.setUser(voMap.get(item.getUserId()));
                })
                .collect(Collectors.toList());
    }

    /**
     * 新增帖子回复
     *
     * @param bizPostsReply 帖子回复
     * @return 结果
     */
    @Override
    public int insertBizPostsReply(BizPostsReply bizPostsReply) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr();
        bizPostsReply.setUserId(SecurityUtils.getUserId());
        bizPostsReply.setOs(userAgent.getOperatingSystem().getName());
        bizPostsReply.setIp(ip);
        bizPostsReply.setLocation(AddressUtils.getRealAddressByIP(ip));
        bizPostsReply.setCreateBy(SecurityUtils.getUsername());
        bizPostsReply.setCreateTime(DateUtils.getNowDate());
        return bizPostsReplyMapper.insertBizPostsReply(bizPostsReply);
    }

    /**
     * 修改帖子回复
     *
     * @param bizPostsReply 帖子回复
     * @return 结果
     */
    @Override
    public int updateBizPostsReply(BizPostsReply bizPostsReply) {
//        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
//        String ip = IpUtils.getIpAddr();
//        bizPostsReply.setOs(userAgent.getOperatingSystem().getName());
//        bizPostsReply.setIp(ip);
//        bizPostsReply.setLocation(AddressUtils.getRealAddressByIP(ip));
        bizPostsReply.setUpdateBy(SecurityUtils.getUsername());
        bizPostsReply.setUpdateTime(DateUtils.getNowDate());
        return bizPostsReplyMapper.updateBizPostsReply(bizPostsReply);
    }

    /**
     * 批量删除帖子回复
     *
     * @param ids 需要删除的帖子回复主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsReplyByIds(Long[] ids) {
        return bizPostsReplyMapper.deleteBizPostsReplyByIds(ids);
    }

    /**
     * 删除帖子回复信息
     *
     * @param id 帖子回复主键
     * @return 结果
     */
    @Override
    public int deleteBizPostsReplyById(Long id) {
        return bizPostsReplyMapper.deleteBizPostsReplyById(id);
    }
}
