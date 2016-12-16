package com.api.foobar.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * Created by jxzhong on 10/18/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "id", "attributes"})
public class ResponseData<T extends Object> implements Serializable {


    @ApiModelProperty(value = "Resource type", required = false, position = 1)
    private String type;

    @ApiModelProperty(value = "Resource ID", required = false, position = 2)
    private String id;

    @ApiModelProperty(value = "Resource content", required = false, position = 3)
    private T attributes;

    public ResponseData() {
    }

    public ResponseData(String type, String id, T attributes) {
        this.type = type;
        this.id = id;
        this.attributes = attributes;
    }

    public ResponseData(String type, String id) {
        this.type = type;
        this.id = id;
    }

    public ResponseData(String type, T attributes) {
        this.type = type;
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getAttributes() {
        return attributes;
    }

    public void setAttributes(T attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);

    }
}
