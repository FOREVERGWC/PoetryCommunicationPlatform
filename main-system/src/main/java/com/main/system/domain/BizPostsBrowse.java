package com.main.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.main.common.annotation.Excel;
import com.main.common.core.domain.BaseEntity;

/**
 * 帖子浏览记录对象 biz_posts_browse
 *
 * @author admin
 * @date 2024-05-14
 */
public class BizPostsBrowse extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 帖子ID */
    @Excel(name = "帖子ID")
    private Long postsId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPostsId(Long postsId)
    {
        this.postsId = postsId;
    }

    public Long getPostsId()
    {
        return postsId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("postsId", getPostsId())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
