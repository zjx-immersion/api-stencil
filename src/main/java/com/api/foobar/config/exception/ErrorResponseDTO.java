package com.api.foobar.config.exception;

import com.api.foobar.contract.ResponseBase;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * Created by jxzhong on 12/16/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO extends ResponseBase  {
//    public ErrorResponseDTO() {
//    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
