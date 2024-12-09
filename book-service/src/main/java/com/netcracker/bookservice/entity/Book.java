package com.netcracker.bookservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;


@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    private UUID bookId;
    @NotEmpty(message = "title cannot be empty")
    private String bookTitle;
    @NotEmpty(message = "Author cannot be empty")
    private String bookAuthor;
    @NotEmpty(message = "Book language cannot be empty")
    private String bookLanguage;
    @NotEmpty(message = "publication cannot be empty")
    private String publication;
    @Max(value = 100,message = "A maximum of 100 copies can be entered")
    @NotNull(message = "please give the number of copies")
    private int noOfCopies;
    private double bookRating;
    @Column(length = 10000)
    private String bookSummary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId",referencedColumnName = "imageId")
    private Image image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private int rentCount=0;

}
