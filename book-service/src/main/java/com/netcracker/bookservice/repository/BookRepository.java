package com.netcracker.bookservice.repository;

import com.netcracker.bookservice.entity.Book;
import com.netcracker.bookservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book,UUID> {
    Optional<Book> findById(UUID bookId);
    List<Book> findByCategory(Category category);

    @Query(value = "select b from Book b where lower(b.bookTitle) like :key or " +
            "lower(b.bookAuthor) like :key or " +
            "lower(b.publication) like :key or " +
            "lower(b.bookSummary) like :key ")
    List<Book> searchByKeywordIgnoreCase(@Param("key") String keyword);


}
