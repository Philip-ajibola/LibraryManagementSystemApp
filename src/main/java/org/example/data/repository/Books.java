package org.example.data.repository;

import org.example.data.model.Book;
import org.example.data.model.BookCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface Books extends MongoRepository<Book,String> {
    void deleteByIsbn(String isbn);

    Book findByIsbn(String isbn);

    List<Book> findByBookCategory(BookCategory category);
    List<Book> findBookByAuthorName(String authorName);
}
