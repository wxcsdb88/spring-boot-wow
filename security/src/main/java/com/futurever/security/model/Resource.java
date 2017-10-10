package com.futurever.security.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * Created by wxcsdb88 on 2017/9/30 10:11.
 */
public class Resource implements Serializable {
    private static final long serialVersionUID = -2772825908529635642L;

    private Long id;
    private Integer type;
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Resource)) return false;
        Resource resource = (Resource) o;
        return Objects.equal(getId(), resource.getId()) &&
                Objects.equal(getType(), resource.getType()) &&
                Objects.equal(getContent(), resource.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getType(), getContent());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("content", content)
                .toString();
    }
}
