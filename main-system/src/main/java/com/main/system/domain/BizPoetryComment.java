package com.main.system.domain;

import com.main.common.annotation.Excel;
import com.main.common.core.domain.BaseEntity;
import com.main.system.domain.vo.SysUserVo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 诗词评论对象 biz_poetry_comment
 *
 * @author admin
 * @date 2024-05-14
 */
public class BizPoetryComment extends BaseEntity {
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
     * 诗词ID
     */
    @Excel(name = "诗词ID")
    private Long poetryId;
    /**
     * 诗词标题
     */
    private String poetryTitle;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 点赞量
     */
    @Excel(name = "点赞量")
    private Long favor;

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

    public Long getPoetryId() {
        return poetryId;
    }

    public void setPoetryId(Long poetryId) {
        this.poetryId = poetryId;
    }

    public String getPoetryTitle() {
        return poetryTitle;
    }

    public BizPoetryComment setPoetryTitle(String poetryTitle) {
        this.poetryTitle = poetryTitle;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFavor() {
        return favor;
    }

    public void setFavor(Long favor) {
        this.favor = favor;
    }

    public SysUserVo getUser() {
        return user;
    }

    public BizPoetryComment setUser(SysUserVo user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("poetryId", getPoetryId())
                .append("poetryTitle", getPoetryTitle())
                .append("userId", getUserId())
                .append("favor", getFavor())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("user", getUser())
                .toString();
    }
}
