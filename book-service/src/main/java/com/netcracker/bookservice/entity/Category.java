package com.netcracker.bookservice.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    private UUID categoryId;
    @NotEmpty(message = "Category name cannot be empty")
    private String categoryName;
    private String categoryDescription;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "imageId",referencedColumnName = "imageId")
    private Image image;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<Book> books=new ArrayList<>();
}
