package org.example.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.example.data.model.BookCategory;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AvailableBookResponse {
    private String id;
    private String author;
    private String isbn;
    private String Title;
    private BookCategory category;
}
