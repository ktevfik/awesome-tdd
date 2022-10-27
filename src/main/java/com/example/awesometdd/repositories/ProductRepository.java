package com.example.awesometdd.repositories;

import com.example.awesometdd.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Tevfik Kadan
 * @created 27/10/2022 - 10:10
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
