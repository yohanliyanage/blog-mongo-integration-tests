package com.yohanliyanage.blog.mongoit.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * A Sample Document.
 * 
 * @author Yohan Liyanage
 * 
 */
@Document
public class Sample {

    @Indexed
    private String key;

    private String value;

    public Sample(String key, String value) {
        super();
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
