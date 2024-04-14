package org.example.services;

import org.example.data.model.Book;
import org.example.data.repository.Books;
import org.example.dto.request.AddBookRequest;
import org.example.dto.request.DeleteBookRequest;
import org.example.dto.response.AddBookResponse;
import org.example.exception.BookAlreadyExistException;
import org.example.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.example.utils.Mapper.map;

@Service
public class BookServiceImpl implements BookServices{
    @Autowired
    private Books books;
    @Override
    public AddBookResponse addbook(AddBookRequest bookRequest) {
        checkIfBookExist(bookRequest);
        Book book = books.save(map(bookRequest));
        return map(book);
    }

    private void checkIfBookExist(AddBookRequest bookRequest) {
        books.findAll().forEach(book -> {if(book.getIsbn().equals(bookRequest.getIsbn()))throw new BookAlreadyExistException("Book ISBN Number Already Exist");});
    }


    @Override
    public String deleteBook(DeleteBookRequest deleteBookRequest) {
        Book book = findBookByIsbn(deleteBookRequest.getIsbn());
        books.delete(book);
        return "Book Deleted Successfully";
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        Book book = books.findByIsbn(isbn);
        if(book == null) throw new BookNotFoundException("Book Not found");
        return book;
    }

    @Override
    public void save(Book book) {
        books.save(book);
    }

    @Override
    public List<Book> getAvailableBooks() {
        List<Book> availableBook = new ArrayList<>();
        books.findAll().forEach(book -> {if(book.isAvailable()) availableBook.add(book);});
        if(availableBook.isEmpty())throw new BookNotFoundException("No Book Found Available");
        return availableBook;
    }
    @Override
    public List<Book> getBorrowedBooks(){
        List<Book> borrowedBook = new ArrayList<>();
        books.findAll().forEach(book -> {if(!book.isAvailable()) borrowedBook.add(book);});
        if(borrowedBook.isEmpty())throw new BookNotFoundException("No Book is Borrowed Yet");
        return borrowedBook;
    }
}
