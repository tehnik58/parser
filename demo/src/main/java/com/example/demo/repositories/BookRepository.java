package com.example.demo.repositories;

import com.example.demo.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findByAuthor(String author);
    List<Book> findByName(String name);

}
