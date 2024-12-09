package com.netcracker.RentalService.rent.dto;

import lombok.*;

import java.util.UUID;


@NoArgsConstructor
@Data
public class Book {
    private UUID bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookLanguage;
    private String publication;
    private String imageName;
    private int noOfCopies;
    private double bookRating;
    private int rentCount;

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", bookLanguage='" + bookLanguage + '\'' +
                ", publication='" + publication + '\'' +
                ", imageName='" + imageName + '\'' +
                ", noOfCopies=" + noOfCopies +
                '}';
    }
}
