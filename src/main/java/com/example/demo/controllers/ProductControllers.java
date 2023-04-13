package com.example.demo.controllers;

import com.example.demo.database.Database;
import com.example.demo.models.Product;
import com.example.demo.models.ResponseObject;
import com.example.demo.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/products")
public class ProductControllers {
    @Autowired
    private ProductRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @GetMapping("")
    ResponseEntity<ResponseObject> getAllProducts() {
        try{
            List<Product> products = repository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query product successfully", products)
            );
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Query product failed", e.toString())
            );
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<Product> foundProduct = repository.findById(id);
        if (foundProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Query product successfully", foundProduct)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Cannot find product with id = " + id, ""));
        }
    }

    @PostMapping("")
    ResponseEntity<ResponseObject> insertProduct(@RequestBody Product newProduct) {
//        List<Product> foundProducts = repository.findByProductName(newProduct.getProductName().trim());
//        if (foundProducts.size() > 0) {
//            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
//                    new ResponseObject("failed", "Product name already taken", "")
//            );
//        }
//        logger.info("" + newProduct.getName())

        try {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Insert product successfully", repository.save(newProduct))
            );
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseObject("failed", e.toString(), "")
            );
        }

    }
//
//    // upsert
//    @PutMapping("/{id}")
//    ResponseEntity<ResponseObject> upsertProduct(@RequestBody Product newProduct, @PathVariable Long id) {
//        Product updatedProduct = repository.findById(id).map(product -> {
//            product.setProductName(newProduct.getProductName());
//            product.setYear(newProduct.getYear());
//            product.setPrice(newProduct.getPrice());
//            product.setUrl(newProduct.getUrl());
//            return repository.save(product);
//        }).orElseGet(() -> {
//            newProduct.setId(id);
//            return repository.save(newProduct);
//        });
//
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject("ok", "Update product successfully", repository.save(updatedProduct)));
//    }
//
//    //delete
//    @DeleteMapping("/{id}")
//    ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
//        boolean exists = repository.existsById(id);
//        if (exists){
//            repository.deleteById(id);
//            return ResponseEntity.status(HttpStatus.OK).body(
//                    new ResponseObject("ok", "Delete product successfully", "")
//            );
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//                new ResponseObject("failed", "Cannot find product to delete", "")
//        );
//    }
}

