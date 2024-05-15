package com.main.system.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.main.common.annotation.Excel;
import com.main.common.core.domain.BaseEntity;

/**
 * 诗词对象 biz_poetry
 *
 * @author admin
 * @date 2024-05-14
 */
public class BizPoetry extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 朝代 */
    @Excel(name = "朝代")
    private String dynasty;

    /** 朗诵 */
    @Excel(name = "朗诵")
    private String recitation;

    /** 视频 */
    @Excel(name = "视频")
    private String video;

    /** 图片 */
    @Excel(name = "图片")
    private String imgs;

    /** 点击量 */
    @Excel(name = "点击量")
    private Long click;

    /** 诗词评论信息 */
    private List<BizPoetryComment> bizPoetryCommentList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getAuthor()
    {
        return author;
    }
    public void setDynasty(String dynasty)
    {
        this.dynasty = dynasty;
    }

    public String getDynasty()
    {
        return dynasty;
    }
    public void setRecitation(String recitation)
    {
        this.recitation = recitation;
    }

    public String getRecitation()
    {
        return recitation;
    }
    public void setVideo(String video)
    {
        this.video = video;
    }

    public String getVideo()
    {
        return video;
    }
    public void setImgs(String imgs)
    {
        this.imgs = imgs;
    }

    public String getImgs()
    {
        return imgs;
    }
    public void setClick(Long click)
    {
        this.click = click;
    }

    public Long getClick()
    {
        return click;
    }

    public List<BizPoetryComment> getBizPoetryCommentList()
    {
        return bizPoetryCommentList;
    }

    public void setBizPoetryCommentList(List<BizPoetryComment> bizPoetryCommentList)
    {
        this.bizPoetryCommentList = bizPoetryCommentList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("author", getAuthor())
            .append("dynasty", getDynasty())
            .append("recitation", getRecitation())
            .append("video", getVideo())
            .append("imgs", getImgs())
            .append("click", getClick())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .append("bizPoetryCommentList", getBizPoetryCommentList())
            .toString();
    }
}
