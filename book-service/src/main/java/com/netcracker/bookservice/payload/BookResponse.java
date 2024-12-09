package com.netcracker.bookservice.payload;

import com.netcracker.bookservice.dto.BookDto;
import com.netcracker.bookservice.entity.Book;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private List<BookDto> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean lastPage;
}
