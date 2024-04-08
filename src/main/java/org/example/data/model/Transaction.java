package org.example.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
@Data
@Document("/transactions")
public class Transaction {
    @Id
    private String id;
    private Book book;
    private User user;
    private BookStatus bookStatus;
    private LocalDate date;
}
