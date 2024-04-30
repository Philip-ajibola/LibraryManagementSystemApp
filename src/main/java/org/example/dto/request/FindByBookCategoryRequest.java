package org.example.dto.request;

import lombok.Data;
import org.example.data.model.BookCategory;
@Data
public class FindByBookCategoryRequest {
    private String username;
    private BookCategory category;
}
