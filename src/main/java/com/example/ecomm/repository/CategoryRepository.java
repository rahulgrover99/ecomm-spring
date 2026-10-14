package com.example.ecomm.repository;

import com.example.ecomm.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT c.name FROM Category c WHERE c.id = :id", nativeQuery = true
    )
    Optional<String> findNameByID(Long id);

    List<Category> findAll();
    Optional<Category> findByNameMatches(String s);
}
