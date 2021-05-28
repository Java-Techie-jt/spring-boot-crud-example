package com.javatechie.crud.example.repository;

import com.javatechie.crud.example.entity.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    Product findByName(String name);
    List<Product> findAllByOrderByNameAsc();
    
    @Query(
    		value = "SELECT * FROM firstwebshop.product_tbl WHERE warranty < CURDATE()",
			nativeQuery = true)
    List<Product> findAllExpiredProduct();
   
}
