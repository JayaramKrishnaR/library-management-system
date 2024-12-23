package com.netcracker.bookservice.repository;


import com.netcracker.bookservice.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category,UUID> {
    Optional<Category> findById(UUID categoryId);
}
