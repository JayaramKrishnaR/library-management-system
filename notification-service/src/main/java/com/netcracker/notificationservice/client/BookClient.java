package com.netcracker.notificationservice.client;

import com.netcracker.notificationservice.dto.BookDto;
import com.netcracker.notificationservice.exceptions.BookServiceNotRespondingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class BookClient {

    private static final String BOOKSERVICE_GETBOOK_URL="http://BOOK-SERVICE/bookService/v1/book/";

    @Autowired
    RestTemplate restTemplate;

    public BookDto getBookById(UUID bookId) throws BookServiceNotRespondingException {
        try {
            BookDto bookDto = restTemplate.getForObject(BOOKSERVICE_GETBOOK_URL + bookId, BookDto.class);
            return bookDto;
        }
        catch(Exception ex){
            throw  new BookServiceNotRespondingException("Book service Not Responding");
        }
    }
}
