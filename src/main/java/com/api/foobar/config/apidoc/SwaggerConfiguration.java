package com.api.foobar.config.apidoc;


import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.api.foobar.config.constant.Constants;
import com.api.foobar.config.properties.SwaggerProperties;
import java.util.List;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.api.foobar.config.constant.CommConstants.*;
import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.builders.PathSelectors.regex;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * Created by on 01.09.16.
 *
 * @author Jianxin Zhong
 *         <p>
 *         Springfox Swagger configuration.
 *         <p>
 *         Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 *         case, you can use a specific Spring profile for this class, so that only front-end developers
 *         have access to the Swagger view.
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties(SwaggerProperties.class)
@Profile({Constants.SPRING_PROFILE_DEVELOPMENT, Constants.SPRING_PROFILE_LOCAL})
//@ConditionalOnExpression("#{!environment.acceptsProfiles('" + Constants.SPRING_PROFILE_NO_SWAGGER + "') && !environment.acceptsProfiles('" + Constants.SPRING_PROFILE_PRODUCTION + "')}")
public class SwaggerConfiguration {

    private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

    public static final String DEFAULT_INCLUDE_PATTERN = REST_BASE_URL_PATTERN + "/.*";

    @Inject
    private TypeResolver typeResolver;

    /**
     * Swagger Springfox configuration.
     *
     * @param swaggerProperties the properties of the application
     * @return the Swagger Springfox configuration
     */
    @Bean
    public Docket swaggerSpringfoxDocket(SwaggerProperties swaggerProperties) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        Contact contact = getContact(swaggerProperties);

        ApiInfo apiInfo = getApiInfo(swaggerProperties, contact);

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .forCodeGeneration(true)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .ignoredParameterTypes(Pageable.class)
//                .ignoredParameterTypes(java.sql.Date.class)
//                .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
//                .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
//                .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(apiPaths())
                .build()
                .pathMapping("/")
                .directModelSubstitute(DateTime.class, String.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .alternateTypeRules(
                        newRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                                typeResolver.resolve(WildcardType.class)))
                .globalOperationParameters(globalOperationParameters())
                .useDefaultResponseMessages(true) //Flag to indicate if default http response codes need to be used or not
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))
                .enableUrlTemplating(false); //if true, use 'from style query' for generated paths

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaulAuth())
                .forPaths(regex("/anyPath.*")) //Selector for the paths this security context applies to
                .build();
    }

    private List<SecurityReference> defaulAuth() {
        return newArrayList(
                new SecurityReference("mykey",
                        newArrayList(new AuthorizationScope("global", "accessEverything"))
                                .toArray(new AuthorizationScope[1]))
        );
    }

    private List<Parameter> globalOperationParameters() {
        List<Parameter> parameterList = newArrayList();

        Parameter correlationIdParameter = new ParameterBuilder()
                .name(CORRELATION_ID_FIELD_NAME)
                .description("Correlation ID (30 chars max: alphanumeric and hyphen)")
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .required(true)
                .build();

        Parameter clientIdParameter = new ParameterBuilder()
                .name(CLIENT_ID_FIELD_NAME)
                .description("Client ID (10 chars max: alphanumeric)")
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .required(true)
                .build();

        Parameter clientVersionParameter = new ParameterBuilder()
                .name(CLIENT_VERSION_FIELD_NAME)
                .description("Client Version (10 chars max: alphanumeric, dot & hyphen)")
                .parameterType("header")
                .modelRef(new ModelRef("string"))
                .required(true)
                .build();

        parameterList.add(correlationIdParameter);
        parameterList.add(clientIdParameter);
        parameterList.add(clientVersionParameter);

        return parameterList;
    }

    private Predicate<String> apiPaths() {
        return regex(DEFAULT_INCLUDE_PATTERN);
    }


    private Contact getContact(SwaggerProperties swaggerProperties) {
        return new Contact(
                swaggerProperties.getContactName(),
                swaggerProperties.getContactUrl(),
                swaggerProperties.getContactEmail());
    }

    private ApiInfo getApiInfo(SwaggerProperties swaggerProperties, Contact contact) {
        return new ApiInfo(
                swaggerProperties.getTitle(),
                swaggerProperties.getDescription(),
                swaggerProperties.getVersion(),
                swaggerProperties.getTermsOfServiceUrl(),
                contact,
                swaggerProperties.getLicense(),
                swaggerProperties.getLicenseUrl());
    }


}
