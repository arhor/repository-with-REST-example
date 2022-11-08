package com.github.arhor.examples.restrep.controller;

import com.github.arhor.examples.restrep.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookController {

    private final BookService bookService;
}
