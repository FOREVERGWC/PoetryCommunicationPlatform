package com.main.system.domain;

import com.main.common.annotation.Excel;
import com.main.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 帖子浏览记录对象 biz_posts_browse
 *
 * @author admin
 * @date 2024-05-14
 */
public class BizPostsBrowse extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 帖子ID
     */
    @Excel(name = "帖子ID")
    private Long postsId;
    /**
     * 帖子标题
     */
    private String postsTitle;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPostsId() {
        return postsId;
    }

    public void setPostsId(Long postsId) {
        this.postsId = postsId;
    }

    public String getPostsTitle() {
        return postsTitle;
    }

    public BizPostsBrowse setPostsTitle(String postsTitle) {
        this.postsTitle = postsTitle;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("postsId", getPostsId())
                .append("postsTitle", getPostsTitle())
                .append("userId", getUserId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
