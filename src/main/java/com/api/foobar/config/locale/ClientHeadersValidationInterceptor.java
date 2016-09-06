package com.api.foobar.config.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import static com.api.foobar.config.constant.CommConstants.*;

/**
 * Created by jxzhong on 9/6/16.
 */
public class ClientHeadersValidationInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("preHandle() - Start");

        String correlationId = request.getHeader(CORRELATION_ID_FIELD_NAME);
        String clientId = request.getHeader(CLIENT_ID_FIELD_NAME);
        String clientVersion = request.getHeader(CLIENT_VERSION_FIELD_NAME);

        BindingResult bindingResult = validateHeader(clientId, clientVersion, correlationId);

        if(bindingResult.hasErrors()) {
            logger.debug("preHandle() - Validation fail: {}", bindingResult);
            throw new BindException(bindingResult);
        }

        logger.debug("preHandle - Validation pass");

        return true;
    }

    private BindingResult validateHeader(String clientId, String clientVersion, String correlationId) {
        ClientHeaderDetailsDTO clientHeaderDetailsDTO = new ClientHeaderDetailsDTO();
        clientHeaderDetailsDTO.setClientId(clientId);
        clientHeaderDetailsDTO.setClientVersion(clientVersion);
        clientHeaderDetailsDTO.setCorrelationId(correlationId);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();

        BindingResult bindingResult = new MapBindingResult(new HashedMap(), clientHeaderDetailsDTO.getClass().getSimpleName());
        SpringValidatorAdapter springValidatorAdapter = new SpringValidatorAdapter(validator);
        springValidatorAdapter.validate(clientHeaderDetailsDTO, bindingResult);

        return bindingResult;
    }

}
