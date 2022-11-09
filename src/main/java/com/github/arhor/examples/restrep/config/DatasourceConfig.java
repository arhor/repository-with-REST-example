package com.github.arhor.examples.restrep.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.arhor.examples.restrep.data.model.Author;
import com.github.arhor.examples.restrep.data.model.Book;
import com.github.arhor.examples.restrep.data.repository.BaseRepository;
import com.github.arhor.examples.restrep.service.dto.AuthorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Slf4j
@Configuration(proxyBeanMethods = false)
public class DatasourceConfig {

    @Value("classpath:data.json")
    private Resource resource;

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public Supplier<Long> longIdGenerator() {
        final var currentId = new AtomicLong(0);
        return currentId::incrementAndGet;
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> datasourceInitializer(
        final ObjectMapper objectMapper,
        final BaseRepository<Author, Long> authorRepository,
        final BaseRepository<Book, Long> bookRepository
    ) {
        return event -> {
            log.info("Initializing datasource...");
            try (var inputStream = resource.getInputStream()) {

                for (var authorDto : objectMapper.readValue(inputStream, AuthorResponseDto[].class)) {
                    var newAuthor = authorRepository.create(
                        new Author(
                            authorDto.firstName(),
                            authorDto.lastName()
                        )
                    );
                    log.info("Created author record: {}", newAuthor);
                    for (var bookDto : authorDto.books()) {
                        var newBook = bookRepository.create(
                            new Book(
                                bookDto.name(),
                                newAuthor.getId()
                            )
                        );
                        log.info("Created book record: {}", newBook);
                    }
                }
                log.info("Datasource successfully initialized");
            } catch (IOException e) {
                log.error("Datasource initialization failed", e);
            }
        };
    }
}
