package com.netcracker.bookservice.serviceimpl;


import com.netcracker.bookservice.enumerations.filterBy;
import com.netcracker.bookservice.dto.BookDto;
import com.netcracker.bookservice.entity.Book;
import com.netcracker.bookservice.entity.Category;
import com.netcracker.bookservice.enumerations.sortBy;
import com.netcracker.bookservice.enumerations.sortDirection;
import com.netcracker.bookservice.exceptions.CopiesNotSufficientException;
import com.netcracker.bookservice.exceptions.ResourceNotFoundException;
import com.netcracker.bookservice.payload.BookResponse;
import com.netcracker.bookservice.repository.BookRepository;
import com.netcracker.bookservice.repository.CategoryRepository;
import com.netcracker.bookservice.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BookServiceImpl implements BookService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public Book createNewBook(Book book,UUID categoryId){
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category"," id ",categoryId));
        book.setCategory(category);
        bookRepository.save(book);
        return book;
    }

    @Override
    public List<BookDto> getBookByCategory(UUID categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category"," id ",categoryId));
        List<Book> books=bookRepository.findByCategory(category);
        List<BookDto> bookDtos=books.stream().map((book)->modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
        return bookDtos;

    }

    @Override
    public Book getBookById(UUID bookId) {
        return  bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Id",bookId));

    }

    @Override
    public BookResponse showBooks(Integer pageNumber, Integer pageSize, sortBy sortBy, sortDirection sortDirection, List<filterBy> filterByList) {
        List<Book> books=bookRepository.findAll();
        Set<Book> bookSet=new HashSet<>();
        for(filterBy filter:filterByList){
            if(filter.equals(filterBy.AVAILABLE)){
                List<Book> available=books.stream().filter(book -> book.getNoOfCopies()>0).collect(Collectors.toList());
                bookSet.addAll(available);
            }
            if(filter.equals(filterBy.NOT_AVAILABLE)){
                List<Book> notAvailable=books.stream().filter(book -> book.getNoOfCopies()==0).collect(Collectors.toList());
                bookSet.addAll(notAvailable);
            }
            if(filter.equals(filterBy.NEWLY_ADDED)){
                List<Book> newlyAdded=books.subList(books.size()-10, books.size());
                bookSet.addAll(newlyAdded);
            }
            if(filter.equals(filterBy.TOP_TRENDING)){
                List<Book> sortedBooksByRating=books.stream().sorted(Comparator.comparing(Book::getBookRating).reversed()).collect(Collectors.toList());
                List<Book> trendingBooks=sortedBooksByRating.subList(0,10);
                bookSet.addAll(trendingBooks);
            }
        }
        BookResponse bookResponse=new BookResponse();
        List<Book> finalList=new ArrayList<>(bookSet);
        List<BookDto> bookDtoList;
        if(bookSet.isEmpty()){
            bookDtoList=books.stream().map((book)->modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
        }
        else{
            bookDtoList=finalList.stream().map((book)->modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
        }
        Pageable pageable=PageRequest.of(pageNumber,pageSize);
        List<BookDto> bookDtos;
        if(sortDirection.equals(sortDirection.DESCENDING)) {
            switch (sortBy) {
                case AUTHOR:
                    bookDtos = bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookAuthor).reversed()).collect(Collectors.toList());
                    break;
                case TITLE:
                    bookDtos= bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookTitle).reversed()).collect(Collectors.toList());
                    break;
                case RATING:
                    bookDtos= bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookRating).reversed()).collect(Collectors.toList());
                    break;
                default:
                    bookDtos= bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookId).reversed()).collect(Collectors.toList());
            }
        }
        else {
            switch (sortBy) {
                case  AUTHOR:
                    bookDtos = bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookAuthor)).collect(Collectors.toList());
                    break;
                case TITLE:
                    bookDtos = bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookTitle)).collect(Collectors.toList());
                    break;
                case RATING:
                    bookDtos = bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookRating)).collect(Collectors.toList());
                    break;
                default:
                    bookDtos = bookDtoList.stream().sorted(Comparator.comparing(BookDto::getBookId)).collect(Collectors.toList());
            }
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), bookDtos.size());
        Page<BookDto> page=new PageImpl<>(bookDtos.subList(start,end),pageable,bookDtoList.size());
        bookResponse.setContent(page.getContent());
        bookResponse.setPageNumber(page.getNumber());
        bookResponse.setPageSize(page.getSize());
        bookResponse.setTotalElements(page.getTotalElements());
        bookResponse.setTotalPages(page.getTotalPages());
        bookResponse.setLastPage(page.isLast());
        return bookResponse;
    }

    @Override
    public BookDto updateBook(Book book, UUID bookId) {
        Book existingBook=bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Id",bookId));
       existingBook.setBookId(bookId);
       existingBook.setBookTitle(book.getBookTitle());
       existingBook.setBookAuthor(book.getBookAuthor());
       existingBook.setPublication(book.getPublication());
       existingBook.setNoOfCopies(book.getNoOfCopies());
       existingBook.setBookLanguage(book.getBookLanguage());
       existingBook.setBookRating(book.getBookRating());
       existingBook.setRentCount(book.getRentCount());
       bookRepository.save(existingBook);
       BookDto bookDto=modelMapper.map(existingBook, BookDto.class);
       bookDto.setImageId(existingBook.getImage().getImageId());
        return bookDto;
    }

    @Override
    public void deleteBook(UUID bookId) {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Id",bookId));
        bookRepository.delete(book);
    }


    @Override
    public Book issueBook(UUID bookId) {
        Book book=bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book","Id",bookId));
        int count=book.getNoOfCopies();
        if(count<0){
            throw new CopiesNotSufficientException("Insufficient copies",false);
        }
        return book;
    }

    @Override
    public List<BookDto> searchBook(String searchKey) {
        List<Book> books =bookRepository.searchByKeywordIgnoreCase("%"+searchKey+"%");
        List<BookDto> bookDtos=books.stream().map((book)->modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
        return bookDtos;
    }

    @Override
    public List<BookDto> searchBookInCategory(UUID categoryId,String keyword) {
       Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
       List<Book> books=bookRepository.findByCategory(category);
       List<Book> sortedBooks=books.stream().filter((book)->
               book.getBookTitle().toLowerCase().contains(keyword) ||
               book.getBookAuthor().toLowerCase().contains(keyword) ||
               book.getPublication().toLowerCase().contains(keyword) ||
               book.getBookSummary().toLowerCase().contains(keyword)).collect(Collectors.toList());
        List<BookDto> bookDtos=sortedBooks.stream().map((book)->modelMapper.map(book, BookDto.class)).collect(Collectors.toList());
        return bookDtos;
    }
}
