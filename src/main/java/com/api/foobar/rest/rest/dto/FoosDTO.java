package com.api.foobar.rest.rest.dto;

import com.api.foobar.domain.Foo;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jxzhong on 12/15/16.
 */
public class FoosDTO implements Serializable {

    private List<Foo> foos;

    public FoosDTO(List<Foo> foos) {
        this.foos = foos;
    }

    public List<Foo> getFoos() {
        return foos;
    }
}
