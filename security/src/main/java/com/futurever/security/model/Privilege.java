package com.futurever.security.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wxcsdb88 on 2017/9/30 10:17.
 */
public class Privilege implements Serializable {
    private static final long serialVersionUID = -6367101532157566116L;

    private Long id;
    private String code;
    private String title;
    private String desc;
    private String url;
    private Map<String, Function> functions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Collection<Function> getFunctions() {
        if ((this.functions == null) || (this.functions.isEmpty())) {
            return null;
        }
        return this.functions.values();
    }

    public void setFunctions(Collection<Function> functions) {
        if ((functions != null) && (!functions.isEmpty())) {
            for (Function function : functions) {
                addFunction(function);
            }
        }
    }

    public void addFunction(Function function) {
        if ((function == null) || (function.getCode() == null)) {
            return;
        }
        if (this.functions == null) {
            this.functions = new HashMap();
        }
        this.functions.put(function.getCode(), function);
    }

    public void removeFunction(Function function) {
        if ((function == null) || (function.getCode() == null)) {
            return;
        }
        this.functions.remove(function.getCode());
    }

    public boolean hasFunction(Function function) {
        if ((function == null) || (function.getCode() == null)) {
            return false;
        }
        return this.functions.containsKey(function.getCode());
    }

    public boolean hasFunction(String functionCode) {
        if (functionCode == null) {
            return false;
        }
        return this.functions.containsKey(functionCode);
    }

    public Function getFunction(String functionCode) {
        if ((this.functions == null) || (this.functions.size() == 0) || (functionCode == null)) {
            return null;
        }
        return (Function) this.functions.get(functionCode);
    }

}
