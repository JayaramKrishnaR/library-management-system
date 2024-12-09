package com.netcracker.bookservice.controller;

import com.netcracker.bookservice.dto.CategoryDto;
import com.netcracker.bookservice.entity.Category;
import com.netcracker.bookservice.entity.Image;
import com.netcracker.bookservice.payload.ApiResponse;
import com.netcracker.bookservice.service.CategoryService;
import com.netcracker.bookservice.service.ImageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/bookService/v1/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;


    @Autowired
    ImageService imageService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createNewCategory(@RequestBody Category category){
        Category newCategory =categoryService.createCategory(category);
        CategoryDto categoryDto=modelMapper.map(newCategory,CategoryDto.class);
         return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PostMapping("/image/{categoryId}")
    public ResponseEntity<CategoryDto> uploadBookImageWithId(
            @RequestParam("image") MultipartFile image,
            @PathVariable UUID categoryId
    ) throws IOException {
        Image imageObject=imageService.uploadImage(image);
        Category category=categoryService.getCategoryById(categoryId);
        category.setImage(imageObject);
        Category updatedCategory=categoryService.updateCategory(category,categoryId);
        CategoryDto categoryDto=modelMapper.map(updatedCategory,CategoryDto.class);
        categoryDto.setImageId(updatedCategory.getImage().getImageId());
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping(value = "/image/{imageId}",produces= MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable UUID imageId,
                                   HttpServletResponse httpServletResponse) throws IOException {
        InputStream inputStream=imageService.serveImage(imageId);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }


    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable UUID categoryId){
        Category category=categoryService.getCategoryById(categoryId);
        CategoryDto categoryDto=modelMapper.map(category,CategoryDto.class);
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> showAllCategories(){
        return ResponseEntity.ok(categoryService.showAllCategories());
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category, @PathVariable UUID categoryId){
        return ResponseEntity.ok(categoryService.updateCategory(category,categoryId));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID categoryId){
        categoryService.deleteCategory(categoryId);

        return ResponseEntity.ok(new ApiResponse("Category deleted successfully",true));
    }
}
