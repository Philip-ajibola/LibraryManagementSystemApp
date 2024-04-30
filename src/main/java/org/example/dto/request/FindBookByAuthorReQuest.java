package org.example.dto.request;

import lombok.Data;

@Data
public class FindBookByAuthorReQuest {
    private String bookAuthor;
    private String username;

}
