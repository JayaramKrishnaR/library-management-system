package com.netcracker.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.CUSTOM)
public class BookDto {

    private UUID bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookLanguage;
    private String publication;
    private int noOfCopies;
    private double bookRating;
    private String bookSummary;
    private UUID imageId;
    private UUID categoryId;
    private int rentCount;

}
