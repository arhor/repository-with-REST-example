package com.github.arhor.examples.restrep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(final String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Profile("dev")
    public <T extends WebApplicationContext & WebServerApplicationContext> ApplicationRunner displayApplicationInfo(
        final T context
    ) {
        return args -> {
            var port = context.getWebServer().getPort();
            var path = Optional.ofNullable(context.getServletContext()).map(ServletContext::getContextPath).orElse("");

            log.info("Local access URL: http://localhost:{}{}", port, path);
        };
    }
}
