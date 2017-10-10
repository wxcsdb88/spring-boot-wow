package com.futurever.security.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by wxcsdb88 on 2017/9/30 10:09.
 */
public class Function implements Serializable {
    private static final long serialVersionUID = 7469977709878195370L;
    private Long id;
    private Privilege privilege;
    private String code;
    private String title;
    private String desc;
    private Collection<Resource> resources;
    private Collection<Function> depends;
    private String[] dependCodes;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Privilege getPrivilege() {
        return this.privilege;
    }

    public void setPrivilege(Privilege privilege) {
        this.privilege = privilege;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Collection<Resource> getResources() {
        return this.resources;
    }

    public void setResources(Collection<Resource> resources) {
        this.resources = resources;
    }

    public void addResource(Resource resource) {
        if (this.resources == null) {
            this.resources = new ArrayList();
        }
        this.resources.add(resource);
    }

    public void addResource(Collection<Resource> resources) {
        if ((resources == null) || (resources.size() == 0)) {
            return;
        }
        if (this.resources == null) {
            this.resources = new ArrayList();
        }
        this.resources = CollectionUtils.union(this.resources, resources);
    }

    public void addURL(String url) {
        Resource r = new Resource();
        r.setContent(url);
        r.setType(Integer.valueOf(1));
        addResource(r);
    }

    public void removeResource(Resource resource) {
        if (this.resources == null) {
            return;
        }
        this.resources.remove(resource);
    }

    public Collection<Function> getDepends() {
        return this.depends;
    }

    public void setDepends(Collection<Function> depends) {
        this.depends = depends;
    }

    public void addDepend(Function function) {
        if (this.depends == null) {
            this.depends = new ArrayList();
        }
        this.depends.add(function);
    }

    public void removeDepend(Function function) {
        if (this.depends == null) {
            return;
        }
        this.depends.remove(function);
    }

    public String[] getDependCodes() {
        return this.dependCodes;
    }

    public void setDependCodes(String[] dependCodes) {
        this.dependCodes = dependCodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Function)) return false;
        Function function = (Function) o;
        return Objects.equal(getId(), function.getId()) &&
                Objects.equal(getPrivilege(), function.getPrivilege()) &&
                Objects.equal(getCode(), function.getCode()) &&
                Objects.equal(getTitle(), function.getTitle()) &&
                Objects.equal(getDesc(), function.getDesc()) &&
                Objects.equal(getResources(), function.getResources()) &&
                Objects.equal(getDepends(), function.getDepends()) &&
                Objects.equal(getDependCodes(), function.getDependCodes());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(), getPrivilege(), getCode(), getTitle(), getDesc(), getResources(), getDepends(), getDependCodes());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("privilege", privilege)
                .add("code", code)
                .add("title", title)
                .add("desc", desc)
                .add("resources", resources)
                .add("depends", depends)
                .add("dependCodes", dependCodes)
                .toString();
    }
}
