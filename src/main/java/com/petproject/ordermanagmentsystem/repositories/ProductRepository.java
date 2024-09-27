package com.petproject.ordermanagmentsystem.repositories;

import com.petproject.ordermanagmentsystem.models.Category;
import com.petproject.ordermanagmentsystem.models.Product;
import com.petproject.ordermanagmentsystem.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);

    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t IN :tags GROUP BY p.id HAVING COUNT(t) = :tagCount")
    List<Product> findByTags(@Param("tags") List<Tag> tags, @Param("tagCount") Long tagCount);

    List<Product> findByNameStartingWith(String prefix);

    @Query("select p from Product p where p.category.name=:categoryName")
    List<Product> findProductsByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p " +
            "JOIN p.tags t " +
            "WHERE t.name IN :tagNames " +
            "GROUP BY p " +
            "HAVING COUNT(t.id) = :tagCount")
    List<Product> findProductsByAllTagNames(@Param("tagNames") List<String> tagNames, @Param("tagCount") long tagCount);
}
