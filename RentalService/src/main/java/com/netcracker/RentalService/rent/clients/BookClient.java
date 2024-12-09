package com.netcracker.RentalService.rent.clients;
import com.netcracker.RentalService.rent.dto.Book;
import com.netcracker.RentalService.rent.exceptions.BookServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class BookClient {
    static final String BOOKSERVICE_GETBOOK_URL="http://BOOK-SERVICE/bookService/v1/issueBook/";
    static final String BOOKSERVICE_UPDATEBOOK_URL="http://BOOK-SERVICE/bookService/v1/book/";

    @Autowired
    RestTemplate restTemplate;
    public Book getBook(UUID bookId) throws BookServiceException {
        Book book;

        try {
            book = restTemplate.getForObject(BOOKSERVICE_GETBOOK_URL + bookId, Book.class);
        }catch (Exception ex){
                throw new BookServiceException("Book service not responding");
        }
        return book;
    }
    public void updateBook(UUID bookId,Book book) throws BookServiceException {
        try {
            restTemplate.put(BOOKSERVICE_UPDATEBOOK_URL + bookId, book);
        }catch (Exception ex){throw new BookServiceException("Book service not responding");}
    }
}
