package com.javatechie.crud.example;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javatechie.crud.example.controller.ProductController;
import com.javatechie.crud.example.entity.Product;
import com.javatechie.crud.example.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;
    //added tests
    @Autowired
    private ObjectMapper objectMapper;

    private Product product1;
    private Product product2;
    private List<Product> productList;

    @BeforeEach
    public void setUp() {
        product1 = new Product(1, "Laptop", 1200, 10);
        product2 = new Product(2, "Smartphone",  800, 25);
        productList = Arrays.asList(product1, product2);
    }

    @Test
    public void testAddProduct() throws Exception {
        when(productService.saveProduct(product1)).thenReturn(product1);

        mockMvc.perform(post("/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product1.getId()))
                .andExpect(jsonPath("$.name").value(product1.getName()))
                .andExpect(jsonPath("$.price").value(product1.getPrice()))
                .andExpect(jsonPath("$.quantity").value(product1.getQuantity()));
    }

    @Test
    public void testAddProducts() throws Exception {
        when(productService.saveProducts(productList)).thenReturn(productList);

        mockMvc.perform(post("/addProducts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[1].id").value(product2.getId()));
    }

    @Test
    public void testFindAllProducts() throws Exception {
        when(productService.getProducts()).thenReturn(productList);

        mockMvc.perform(get("/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(product1.getId()))
                .andExpect(jsonPath("$[1].id").value(product2.getId()));
    }

    @Test
    public void testFindProductById() throws Exception {
        when(productService.getProductById(1)).thenReturn(product1);

        mockMvc.perform(get("/productById/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product1.getId()))
                .andExpect(jsonPath("$.name").value(product1.getName()));
    }

    @Test
    public void testFindProductByName() throws Exception {
        when(productService.getProductByName("Laptop")).thenReturn(product1);

        mockMvc.perform(get("/product/Laptop")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(product1.getName()));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        product1.setName("Updated Laptop");
        when(productService.updateProduct(product1)).thenReturn(product1);

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Laptop"));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        when(productService.deleteProduct(1)).thenReturn("Product removed !! " + product1.getId());

        mockMvc.perform(delete("/delete/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Product removed !! 1"));
    }
}
