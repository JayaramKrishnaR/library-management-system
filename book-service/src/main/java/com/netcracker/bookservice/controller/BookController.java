package com.netcracker.bookservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.bookservice.enumerations.filterBy;
import com.netcracker.bookservice.dto.BookDto;
import com.netcracker.bookservice.entity.Book;
import com.netcracker.bookservice.entity.Image;
import com.netcracker.bookservice.enumerations.sortBy;
import com.netcracker.bookservice.enumerations.sortDirection;
import com.netcracker.bookservice.payload.ApiResponse;
import com.netcracker.bookservice.payload.BookResponse;
import com.netcracker.bookservice.service.BookService;
import com.netcracker.bookservice.service.ImageService;
import org.json.JSONObject;
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
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/bookService/v1")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    ImageService imageService;


    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ObjectMapper objectMapper;

    @PostMapping("/category/{categoryId}/book/")
    public ResponseEntity<BookDto> createBook( @RequestBody Book book,
                                           @PathVariable UUID categoryId){
        Book newBook=bookService.createNewBook(book,categoryId);
        BookDto bookDto=modelMapper.map(newBook, BookDto.class);
        return new ResponseEntity<>(bookDto, HttpStatus.CREATED);
    }

    @PostMapping("/book/image/{bookId}")
    public ResponseEntity<BookDto> uploadBookImageWithId(
            @RequestParam("image") MultipartFile image,
            @PathVariable UUID bookId
    ) throws IOException {
        Image imageObject=imageService.uploadImage(image);
        Book book=bookService.getBookById(bookId);
        book.setImage(imageObject);

        BookDto updatedBook=bookService.updateBook(book,bookId);

        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping(value = "/book/image/{imageId}",produces= MediaType.IMAGE_JPEG_VALUE)
    public void serveImage(@PathVariable UUID imageId,
                           HttpServletResponse httpServletResponse) throws IOException {
        InputStream inputStream=imageService.serveImage(imageId);
        httpServletResponse.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }

    @GetMapping("/book/category/{categoryId}")
    public ResponseEntity<List<BookDto>> getBookUsingCategory(@PathVariable UUID categoryId){
        List<BookDto> books=bookService.getBookByCategory(categoryId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book")
    public ResponseEntity<BookResponse> showAllBooks(@RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
                                                     @RequestParam(value="pageSize",defaultValue = "12",required = false) Integer pageSize,
                                                     @RequestParam(value = "sortBy",defaultValue = "BOOK_ID",required = false) sortBy sortBy,
                                                     @RequestParam(value="sortDirection",defaultValue = "ASCENDING",required = false) sortDirection sortDirection,
                                                     @RequestParam(value="filterBy",defaultValue ="NONE",required = false) List<filterBy> filterByList){
        BookResponse bookResponse =bookService.showBooks(pageNumber,pageSize,sortBy,sortDirection,filterByList);
        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable UUID bookId){
        Book book=bookService.getBookById(bookId);
        BookDto bookDto=modelMapper.map(book, BookDto.class);
        return ResponseEntity.ok(bookDto);

    }

    @PutMapping("/book/{bookId}")
    public ResponseEntity<BookDto> updateBookById( @RequestBody Book book,@PathVariable UUID bookId){
        BookDto updatedBook=bookService.updateBook(book,bookId);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse> deleteBookById(@PathVariable UUID bookId){
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(new ApiResponse("Book deleted successfully",true),HttpStatus.OK);
    }


    @GetMapping("/issueBook/{bookId}")
    public Book issueABook(@PathVariable UUID bookId){
        return bookService.issueBook(bookId);
    }

    @GetMapping("/search/book/{keyword}")
    public ResponseEntity<List<BookDto>> searchBookByKey(@PathVariable String keyword){
        List<BookDto> books=bookService.searchBook(keyword.toLowerCase());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/category/{categoryId}/search/{keyword}")
    public ResponseEntity<List<BookDto>> searchBookInCategory(@PathVariable UUID categoryId,
                                                              @PathVariable String keyword){
        List<BookDto> bookDtos=bookService.searchBookInCategory(categoryId,keyword.toLowerCase());
        return ResponseEntity.ok(bookDtos);
    }

    @GetMapping("/showBookAsGson")
    public ResponseEntity<BookDto> showGsonBooks() throws JsonProcessingException {
        BookDto bookDto = new BookDto();
        bookDto.setBookRating(2);
        bookDto.setBookAuthor(null);
        bookDto.setBookSummary("Nothing summary");
        String JSONBook = objectMapper.writeValueAsString(bookDto);
//        System.out.println("BookDto  ====> "+bookDto);
//        System.out.println("JSON  ===>"+JSONBook);
//        JSONObject jsonObject = new JSONObject(JSONBook);
//        System.out.println("JSON OBJECT  ===>"+JSONBook);
//        String formattedJsonString = JSONObject.quote(jsonObject.toString());
//        System.out.println("formatted String  ===>"+formattedJsonString);
//        String address = "2602 ahkjsdgds";
//        String formattedAddress = "\"\\\"" + address + "\"\\\"";
//        Map<String,Object> longi = new HashMap<>();
//        longi.put("LONGITUDE",181818);
//        String stringJson = objectMapper.writeValueAsString(longi);
//        JSONObject jsonObjectlongi = new JSONObject(stringJson);
//        String longiFormat = JSONObject.quote(jsonObjectlongi.toString());
//        Map<String,Object> lati = new HashMap<>();
//        lati.put("LATITUDE",181818);
//        String stringJsonlati = objectMapper.writeValueAsString(lati);
//        JSONObject jsonObjectlatii = new JSONObject(stringJsonlati);
//        String latiFormat = JSONObject.quote(jsonObjectlatii.toString());
//        List<String> coordinates = new ArrayList<>();
//        coordinates.add(longiFormat);
//        coordinates.add(latiFormat);
        String someFinalString = objectMapper.writeValueAsString(JSONBook);
        return new ResponseEntity<>(bookDto,HttpStatus.OK);
    }

}
