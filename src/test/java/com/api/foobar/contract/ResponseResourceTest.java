package com.api.foobar.contract;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jxzhong on 12/15/16.
 */
public class ResponseResourceTest {

    private Validator validator;


    @Before
    public void setUp() throws Exception {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    @Test
    public void testDataNotNull() throws Exception {

    }


    public class RequestResourceDummyDTO extends
}