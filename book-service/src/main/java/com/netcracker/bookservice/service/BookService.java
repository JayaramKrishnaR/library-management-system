package com.netcracker.bookservice.service;

import com.netcracker.bookservice.enumerations.filterBy;
import com.netcracker.bookservice.dto.BookDto;
import com.netcracker.bookservice.entity.Book;
import com.netcracker.bookservice.enumerations.sortBy;
import com.netcracker.bookservice.enumerations.sortDirection;
import com.netcracker.bookservice.payload.BookResponse;


import java.util.List;
import java.util.UUID;

public interface BookService {

    Book createNewBook(Book book, UUID categoryId);


    List<BookDto> getBookByCategory(UUID categoryId);

    Book getBookById(UUID bookId);

    BookResponse showBooks(Integer pageNumber, Integer pageSize, sortBy sortBy, sortDirection sortDirection, List<filterBy> filterByList);

    BookDto updateBook(Book book,UUID bookId);

    void deleteBook(UUID bookId);


    Book issueBook(UUID bookId);

    List<BookDto> searchBook(String searchKey);

    List<BookDto> searchBookInCategory(UUID categoryId,String keyword);
}
