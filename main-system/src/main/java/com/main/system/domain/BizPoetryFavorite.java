package com.main.system.domain;

import com.main.common.annotation.Excel;
import com.main.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户诗词收藏对象 biz_poetry_favorite
 *
 * @author admin
 * @date 2024-05-14
 */
public class BizPoetryFavorite extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

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
     * 诗词
     */
    private BizPoetry bizPoetry;

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

    public String getPoetryTitle() {
        return poetryTitle;
    }

    public BizPoetryFavorite setPoetryTitle(String poetryTile) {
        this.poetryTitle = poetryTile;
        return this;
    }

    public Long getPoetryId() {
        return poetryId;
    }

    public void setPoetryId(Long poetryId) {
        this.poetryId = poetryId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BizPoetry getBizPoetry() {
        return bizPoetry;
    }

    public BizPoetryFavorite setBizPoetry(BizPoetry bizPoetry) {
        this.bizPoetry = bizPoetry;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("poetryId", getPoetryId())
                .append("poetryTitle", getPoetryTitle())
                .append("userId", getUserId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
