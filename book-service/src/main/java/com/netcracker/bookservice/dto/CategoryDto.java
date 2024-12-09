package com.netcracker.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryDto {
    private UUID categoryId;
    private String categoryName;
    private String categoryDescription;
    private UUID imageId;
}
