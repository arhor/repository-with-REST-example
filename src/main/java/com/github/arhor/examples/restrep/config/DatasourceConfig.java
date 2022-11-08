package com.github.arhor.examples.restrep.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Configuration(proxyBeanMethods = false)
public class DatasourceConfig {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Supplier<Long> longIdGenerator() {
        final var currentId = new AtomicLong(0);
        return currentId::incrementAndGet;
    }
}
