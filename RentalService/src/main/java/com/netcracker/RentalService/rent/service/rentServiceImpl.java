package com.netcracker.RentalService.rent.service;

import com.netcracker.RentalService.rent.clients.BookClient;
import com.netcracker.RentalService.rent.clients.UserClient;
import com.netcracker.RentalService.rent.dto.Book;
import com.netcracker.RentalService.rent.enumerations.Status;
import com.netcracker.RentalService.rent.dto.User;
import com.netcracker.RentalService.rent.entity.Rent;
import com.netcracker.RentalService.rent.dao.*;
import com.netcracker.RentalService.rent.exceptions.BookServiceException;
import com.netcracker.RentalService.rent.exceptions.InsufficientBalanceException;
import com.netcracker.RentalService.rent.exceptions.RentFailureException;
import com.netcracker.RentalService.rent.exceptions.UserServiceException;
import com.netcracker.RentalService.reservation.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;

@Service
public class rentServiceImpl implements RentService {
    @Autowired
    RentRepository rentRepository;
    @Autowired
    UserClient userClient;
    @Autowired
    BookClient bookClient;
    @Autowired
    MessageService messageService;
    @Autowired
    ModelMapper mapper;

    Logger LOGGER=LoggerFactory.getLogger(rentServiceImpl.class);

    @Override
    public String rateBookById(UUID bookId, int rating) throws BookServiceException {
        Book book=bookClient.getBook(bookId);
        int count=book.getRentCount();
        double rate=((book.getBookRating()*count)+rating)/(count+1);
        book.setRentCount(count+1);
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        book.setBookRating(Double.valueOf(twoDForm.format(rate)));
        System.out.println(Double.valueOf(twoDForm.format(rate)));
        bookClient.updateBook(bookId,book);
        return "Book rated Successfully";
    }

    @Override

    public Rent addRentRecord(UUID userId, UUID bookId) throws RentFailureException, InsufficientBalanceException, BookServiceException, UserServiceException {

        User user;
        Book book;
        Rent rent = new Rent();
        user = userClient.getUser(userId);
        if (user.getCredit() < 0) {
            throw new InsufficientBalanceException();
        }
        try {

            Book clientBook =bookClient.getBook(bookId);
            book = mapper.map(clientBook, Book.class);

            rent.setBook_id(book.getBookId());
            rent.setStatus(Status.ISSUED);
            rent.setBook_title(book.getBookTitle());
            rent.setUser_name(user.getFirst_name());
            rent.setUserId(user.getUserId());
            rent.setIssue_date(LocalDate.now());
            rent.setDue_date(user.getSubEndDate());
            user.setCredit(user.getCredit() - 1);

            book.setNoOfCopies(book.getNoOfCopies() - 1);
            userClient.updateUser(userId, user);
            try {
                bookClient.updateBook(bookId, book);
            }catch (Exception exception){user.setCredit(user.getCredit()+1);userClient.updateUser(userId,user);
            throw new RentFailureException("Rent failed");}
            return rentRepository.save(rent);

        } catch (Exception ex) {
            throw new RentFailureException("Rent failed");
        }

    }

    @Override
    public List<Rent> getRentByUser(UUID id) {
        return rentRepository.findByUserId(id);
    }

    @Override
    public List<Rent> getBookReturnByUser(Status status,UUID id) {
        return rentRepository.findByStatus(status,id);
    }

    @Override
    public Rent updateRent(UUID rentId) throws BookServiceException {
        Rent existingRent=rentRepository.findById(rentId).get();
        Book book=bookClient.getBook(rentRepository.findById(rentId).get().getBook_id());
        book.setNoOfCopies(book.getNoOfCopies()+1);
        bookClient.updateBook(book.getBookId(),book);
        existingRent.setDue_date(LocalDate.now());
        existingRent.setStatus(Status.RETURNED);
        return rentRepository.save(existingRent);
    }

    @Override
    @Scheduled(cron = "0 0 10 * * *")
    public void sendExpirationNotification() {
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String currentDate=LocalDate.parse(LocalDate.now().toString(),formatter).toString();
        LOGGER.info("Current date :"+currentDate);
        LocalDate endDate=LocalDate.now().plusDays(7);

        String endingDate=LocalDate.parse(endDate.toString(),formatter).toString();
        LOGGER.info("Ending Date :"+endingDate);

        LOGGER.info("Subscription Ending users : "+userClient.getSubEndingUsers(currentDate,endingDate).toString());

        List<User> SubEndUsers=userClient.getSubEndingUsers(currentDate,endingDate);

        SubEndUsers.stream().forEach((user)->messageService.sendSubscriptionNotification(user));


    }
}
