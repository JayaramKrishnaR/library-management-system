package com.netcracker.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private UUID bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookLanguage;
    private String publication;
    private int noOfCopies;
    private int bookRating;
    private String bookSummary;
    private UUID imageId;
    private UUID categoryId;

}
