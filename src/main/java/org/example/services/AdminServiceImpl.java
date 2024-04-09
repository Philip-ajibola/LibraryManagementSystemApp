package org.example.services;

import org.example.data.model.Librarian;
import org.example.data.model.Book;
import org.example.data.model.Transaction;
import org.example.data.repository.AdminRepository;
import org.example.data.repository.Transactions;
import org.example.dto.request.*;
import org.example.dto.response.AddBookResponse;
import org.example.dto.response.RegisterAdminResponse;
import org.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;

import static org.example.utils.Mapper.map;

@Service
public class AdminServiceImpl implements AdminServices {
    @Autowired
    private Transactions transactions;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BookServices bookServices;
    @Override
    public RegisterAdminResponse registerAdmin(RegisterAdminRequest registerAdminRequest) {
        validateRequest(registerAdminRequest);
       if(adminRepository.count()==1)throw new AdminExistException("Admin Already Exist");
       Librarian admin = adminRepository.save(map(registerAdminRequest));
        return map(admin);
    }


    @Override
    public void addTransaction(Transaction transaction, String username) {
        Book book = transaction.getBook();
        validateAdmin(username.toLowerCase());
        validateLogin(username.toLowerCase());
        transactions.save(transaction);
    }

    private void validateAdmin(String username) {
        if(username.isEmpty())throw new InvalidUserNameException("Provide A valid UserName");
        Librarian admin = adminRepository.findByUsername(username.toLowerCase());
        if(admin.getUsername() == null)throw new UserNotFoundException("Admin Not Found");
    }

    @Override
    public RegisterAdminResponse resetAdmin(ResetAdminRequest resetAdminRequest) {
        Librarian admin = adminRepository.findByUsername(resetAdminRequest.getOldUsername().toLowerCase());
        admin.setUsername(resetAdminRequest.getUsername().toLowerCase());
        admin.setPassword(resetAdminRequest.getPassword());
        adminRepository.save(admin);
        return map(admin);
    }


    @Override
    public AddBookResponse addBook(AddBookRequest bookRequest) {
        validateAdmin(bookRequest.getAdminName().toLowerCase());
        validateLogin(bookRequest.getAdminName().toLowerCase());
        return bookServices.addbook(bookRequest);
    }
    @Override
    public String  deleteBook(DeleteBookRequest deleteBookRequest) {
        validateAdmin(deleteBookRequest.getAdminName().toLowerCase());
        validateLogin(deleteBookRequest.getAdminName().toLowerCase());
        Book book = bookServices.findBookByIsbn(deleteBookRequest.getIsbn());
        if(book == null)throw new BookNotFoundException("Book not Found");
        return bookServices.deleteBook(deleteBookRequest);
    }

    @Override
    public String login(LogInRequest logInRequest) {
        validateAdmin(logInRequest.getUsername().toLowerCase());
        Librarian admin = adminRepository.findByUsername(logInRequest.getUsername().toLowerCase());
        if(!logInRequest.getPassword().equals(admin.getPassword()))throw new InvalidPasswordException("Wrong password");
        admin.setLoggedIn(true);
        adminRepository.save(admin);
        return "Login successful";
    }
    private static void validateRequest(RegisterAdminRequest registerAdminRequest) {
        if(!registerAdminRequest.getUsername().matches("[a-zA-Z0-9]+"))throw new InvalidUserNameException("Username Can Only Contain Alphabet And Number and not null");
        if(registerAdminRequest.getPassword().trim().isEmpty())throw new InvalidPasswordException("Provide A Valid Password");
    }


    @Override
    public String logout(LogOutRequest logOutRequest) {
        Librarian admin = adminRepository.findByUsername(logOutRequest.getUsername().toLowerCase());
        admin.setLoggedIn(false);
        adminRepository.save(admin);
        return "logout SuccessFul";
    }
    private void validateLogin(String username){
        Librarian admin = adminRepository.findByUsername(username.toLowerCase());
        validateAdmin(username);
        if(!admin.isLoggedIn())throw new LoginException("Admin is not loggedIn");
    }

    @Override
    public List<Transaction> getTransactionHistory(String username) {
        validateAdmin(username.toLowerCase());
        if(transactions.findAll().isEmpty()) throw new NoTransactionException("No Transaction Made Yet");
        return transactions.findAll();
    }


    @Override
    public List<Book> getAvailablebooks(String username) {
       validateAdmin(username.toLowerCase());
        return  bookServices.getAvailableBooks();
    }

    @Override
    public List<Book> getBorrowedBook(String username) {
        validateAdmin(username.toLowerCase());
        return bookServices.getBorrowedBooks();
    }



}
