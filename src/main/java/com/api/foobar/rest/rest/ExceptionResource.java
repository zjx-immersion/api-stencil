package com.api.foobar.rest.rest;

import com.api.foobar.config.exception.BadRequestException;
import org.apache.commons.lang.NullArgumentException;
import org.springframework.transaction.InvalidIsolationLevelException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jxzhong on 12/16/16.
 */

@RestController
@RequestMapping({"/api"})
public class ExceptionResource {

    @RequestMapping("/ex1")
    public String ex1() {
        // will be catched by global exception handler method handleBaseException
        throw new BadRequestException("Base Exception");
    }

    @RequestMapping("/ex2")
    public String ex2() {
        // will be catched by global exception handler method handleBaseException
        throw new InvalidIsolationLevelException("General error");
    }

    @RequestMapping("/ex3")
    public String ex3() {
        // will be catched by global exception handler method handleBaseException
        throw new NullArgumentException("There is no params");
    }

    @RequestMapping("/ex4")
    public String ex4() {
        // will be catched by global exception handler method handleException
        throw new NullPointerException("null pointer exception");
    }

    @RequestMapping("/ex5")
    public String ex5() {
        // will be catched by controller exception hnadler handler method nfeHandler
        throw new NumberFormatException("number format exception");
    }

}
