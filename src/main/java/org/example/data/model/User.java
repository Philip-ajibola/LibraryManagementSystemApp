package org.example.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("user")
public class User {
    private String username;
    private String password;
    private boolean isLoggedIn;
    private List<Book> borrowBookList = new ArrayList<>();
    @Id
    private String id;
}
