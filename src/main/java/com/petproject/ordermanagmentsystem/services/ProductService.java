package com.petproject.ordermanagmentsystem.services;

import com.petproject.ordermanagmentsystem.models.Category;
import com.petproject.ordermanagmentsystem.models.Order;
import com.petproject.ordermanagmentsystem.models.Product;
import com.petproject.ordermanagmentsystem.models.Tag;
import com.petproject.ordermanagmentsystem.repositories.ProductRepository;
import com.petproject.ordermanagmentsystem.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)

public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void createNewProduct(Product product){
        productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(int id){
        return productRepository.findById(id).orElse(null);
    }


    @Transactional
    public Product updateOrderById(int id, Product product) {
        if(productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        else throw new ResourceNotFoundException("Product with id "+ id + " does not exist!");
    }

    @Transactional
    public void deleteProductById(int id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        }
        else throw new ResourceNotFoundException("Product with id "+ id + " does not exist!");
    }

    public List<Product> getProductsByCategoryName(String categoryName){
        return productRepository.findProductsByCategoryName(categoryName);
    }

    public List<Product> getProductsByTags(List<Tag> tags) {
        return productRepository.findByTags(tags, (long) tags.size());
    }

    public List<Product> getProductsByTagsName(List<String> tagsName){
        return  productRepository.findProductsByAllTagNames(tagsName, tagsName.size());
    }

    public List<Product> getProductsByNameStartingWith(String prefix) {
        return productRepository.findByNameStartingWith(prefix);
    }




}
