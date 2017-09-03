package com.futurever.elm.api.dto;/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:47
 **/

import java.util.Date;

/**
 * Description:
 * TODO
 *
 * @author wxcsdb88
 * @since 2017-09-03 19:47
 **/
public class OrderDTO {
    private Long id;
    private Long userId;
    private String description;
    private String content;
    /*创建者id*/
    private Long createUserId;
    /*创建者标识*/
    private String createUserName;
    /*创建时间*/
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", createUserId=" + createUserId +
                ", createUserName='" + createUserName + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
