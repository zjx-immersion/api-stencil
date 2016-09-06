package com.api.foobar.web.rest.errors;

import java.io.Serializable;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *
 */

public class FieldErrorDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorDTO(String dto, String field, String message) {
        this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

}
