package org.example.data.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("admin")
@RequiredArgsConstructor
public class Admin {
    @Id
    private String id;
    private String username;
    private String password;
    private boolean isLoggedIn;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
