package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BorrowBookResponseForUser {
    private String bookId;
    private String bookTitle;
    private String bookAuthor;
    private String Category;
}
