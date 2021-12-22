package com.javatechie.crud.example.repositoryTest;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository repo;
	
	@Test
	public void testAddProduct() {
		
		Product product = new Product();
		product.setName("TV");
		product.setQuantity(100);
		product.setPrice(3000.00);
		
		Product savedProduct = repo.save(product);
		
		Assertions.assertThat(savedProduct).isNotNull();
		Assertions.assertThat(savedProduct.getId()).isGreaterThan(0);
		Assertions.assertThat(savedProduct.getName()).isEqualTo("TV");
		
		
	}
	
	@Test
	public void testListAllProducts() {
		Iterable<Product> products = repo.findAll();
		Assertions.assertThat(products).hasSizeGreaterThan(0);
		for(Product p : products) {
			System.out.println(p);
		}
	}
	
	@Test
	public void testUpdateProduct() {
		Integer productId = 4;
		Optional<Product> optionalProduct = repo.findById(productId);
		Product product = optionalProduct.get();
		product.setName("Sofá");
		product.setQuantity(15);
		product.setPrice(2500.00);
		repo.save(product);

		Product updatedProduct = repo.findById(productId).get();

		Assertions.assertThat(updatedProduct.getName()).isEqualTo("Sofá");
	}
	
	@Test
	public void testFindProductById() {
		Integer productId = 2 ; 
		Optional<Product> optionalProduct = repo.findById(productId);

		Assertions.assertThat(optionalProduct).isPresent();
		System.out.println(optionalProduct.get());
	}

	@Test
	public void testDeleteProductById() {
		Integer productId= 4;
		repo.deleteById(productId);

		Optional<Product> optionalProduct = repo.findById(productId);

		Assertions.assertThat(optionalProduct).isNotPresent();
	}
}
