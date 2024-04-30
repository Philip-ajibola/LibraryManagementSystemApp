package org.example.services;

import org.example.data.model.Book;
import org.example.data.model.BookCategory;
import org.example.dto.request.AddBookRequest;
import org.example.dto.request.DeleteBookRequest;
import org.example.dto.response.AddBookResponse;

import java.util.List;

public interface BookServices {
    AddBookResponse addbook(AddBookRequest bookRequest);

    String deleteBook(DeleteBookRequest deleteBookRequest);

    Book findBookByIsbn(String isbn);

    void save(Book book);
    List<Book> findBookByCategory(BookCategory category);

    List<Book> getAvailableBooks();
    List<Book> findBookByAuthor(String author);
    List<Book> getBorrowedBooks();

    List<Book> findAll();

    Book findBookById(String bookId);
}
