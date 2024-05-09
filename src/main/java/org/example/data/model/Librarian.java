package org.example.data.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("admin")
@RequiredArgsConstructor
public class Librarian {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn;
}
