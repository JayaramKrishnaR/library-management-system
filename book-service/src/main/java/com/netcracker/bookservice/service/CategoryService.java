package com.netcracker.bookservice.service;

import com.netcracker.bookservice.dto.CategoryDto;
import com.netcracker.bookservice.entity.Category;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    Category createCategory(Category category);

    List<CategoryDto> showAllCategories();

    Category getCategoryById(UUID categoryId);

    Category updateCategory(Category category,UUID categoryId);

    void deleteCategory(UUID categoryId);
}
