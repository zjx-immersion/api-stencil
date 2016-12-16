package com.api.foobar.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jxzhong on 10/18/16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class ResponseResource<T extends Object> extends ResponseBase {
    @ApiModelProperty(value = "Data", required = false)
    private List<ResponseData<T>> data;

    public List<ResponseData<T>> getData() {
        return data;
    }

    public void setData(List<ResponseData<T>> data) {
        this.data = data;
    }

    public void addData(ResponseData<T> data) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(data);
    }

}
