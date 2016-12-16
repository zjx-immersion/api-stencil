package com.api.foobar.rest.rest.dto;

import com.api.foobar.contract.ResponseData;
import com.api.foobar.contract.ResponseResource;
import com.api.foobar.domain.Foo;
import java.io.Serializable;

/**
 * Created by jxzhong on 12/15/16.
 */
public class ResponseResourceDTO extends ResponseResource<Foo> implements Serializable {

    public ResponseResourceDTO(FoosDTO foos) {
        String type = "Foo";
        foos.getFoos().forEach(data -> addData(new ResponseData(type, data)));
    }
}
