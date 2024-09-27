package com.petproject.ordermanagmentsystem.controllers;


import com.petproject.ordermanagmentsystem.models.Category;
import com.petproject.ordermanagmentsystem.models.Product;
import com.petproject.ordermanagmentsystem.services.OrderService;
import com.petproject.ordermanagmentsystem.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;

    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> productList = productService.getAllProducts();
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/by_category/{categoryName}")
    public ResponseEntity<List<Product>> getProductsByCategoryName(@PathVariable String categoryName){
        List<Product> productList = productService.getProductsByCategoryName(categoryName);
        return ResponseEntity.ok(productList);
    }

    @GetMapping("/by-tags")
    public  ResponseEntity<List<Product>> getProductsByTags(@RequestParam List<String> tagNames){
        List<Product> productList = productService.getProductsByTagsName(tagNames);
        return ResponseEntity.ok(productList);
    }


    @GetMapping("/by-name")
    public ResponseEntity<List<Product>> getProductsByName(@RequestParam  String productName){
        List<Product> productList = productService.getProductsByNameStartingWith(productName);
        return ResponseEntity.ok(productList);
    }






}
