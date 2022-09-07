package com.daypaytechnologies.digichitfund.app.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.categoryName =:categoryName ")
    Optional<Category> findByCategoryName (String categoryName);

    @Query("select c from Category c where c.id =:id ")
    Optional<Category> findByCategoryId (@Param("id") Long id);
}
