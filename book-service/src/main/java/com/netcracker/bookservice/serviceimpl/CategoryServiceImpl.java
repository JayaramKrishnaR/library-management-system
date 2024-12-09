package com.netcracker.bookservice.serviceimpl;

import com.netcracker.bookservice.dto.CategoryDto;
import com.netcracker.bookservice.entity.Category;
import com.netcracker.bookservice.exceptions.ResourceNotFoundException;
import com.netcracker.bookservice.repository.CategoryRepository;
import com.netcracker.bookservice.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
   @Autowired
   CategoryRepository categoryRepository;

   @Autowired
   ModelMapper modelMapper;

    @Override
    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }

    @Override
    public List<CategoryDto> showAllCategories() {
        List<Category> categories=categoryRepository.findAll();
        List<CategoryDto> categoryDtos=categories.stream().map((category)-> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category"," id ",categoryId));
    }

    @Override
    public Category updateCategory(Category category, UUID categoryId) {
        Category existing=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category"," id ",categoryId));
        existing.setCategoryId(categoryId);
        existing.setCategoryName(category.getCategoryName());
        existing.setCategoryDescription(category.getCategoryDescription());
        existing.setImage(category.getImage());
        return categoryRepository.save(existing);
    }

    @Override
    public void deleteCategory(UUID categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category"," id ",categoryId));
        categoryRepository.delete(category);
    }
}
