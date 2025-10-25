package com.ecommerce.spring_ecommerce.repository;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);

    List<Product> findByCategoryOrderByPriceAsc(Category category);

    @Query("SELECT e FROM Product e WHERE LOWER(e.name) LIKE %:kw% OR LOWER(e.description) LIKE %:kw%")
    List<Product> findByKeyword(@Param("kw") String keyword);

}
