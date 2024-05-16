package com.main.system.domain;

import com.main.common.annotation.Excel;
import com.main.common.core.domain.TreeEntity;
import com.main.system.domain.vo.SysUserVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 帖子回复对象 biz_posts_reply
 *
 * @author admin
 * @date 2024-05-16
 */
public class BizPostsReply extends TreeEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 帖子ID
     */
    @Excel(name = "帖子ID")
    private Long postsId;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 回复ID
     */
    @Excel(name = "回复ID")
    private Long replyId;

    /**
     * 操作系统
     */
    @Excel(name = "操作系统")
    private String os;

    /**
     * IP
     */
    @Excel(name = "IP")
    private String ip;

    /**
     * IP属地
     */
    @Excel(name = "IP属地")
    private String location;

    private SysUserVo user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public SysUserVo getUser() {
        return user;
    }

    public BizPostsReply setUser(SysUserVo user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("postsId", getPostsId())
                .append("userId", getUserId())
                .append("replyId", getReplyId())
                .append("os", getOs())
                .append("ip", getIp())
                .append("location", getLocation())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
