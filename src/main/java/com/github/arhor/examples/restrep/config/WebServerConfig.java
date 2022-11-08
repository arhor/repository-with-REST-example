package com.github.arhor.examples.restrep.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.web.method.HandlerTypePredicate.forAnnotation;

@Configuration(proxyBeanMethods = false)
public class WebServerConfig implements WebMvcConfigurer {

    public void configurePathMatch(final PathMatchConfigurer configurer) {
        configurer.addPathPrefix("/api", forAnnotation(RestController.class));
    }
}
