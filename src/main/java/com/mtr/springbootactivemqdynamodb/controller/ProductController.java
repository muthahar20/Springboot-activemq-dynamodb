package com.mtr.springbootactivemqdynamodb.controller;

import com.mtr.springbootactivemqdynamodb.entity.Product;
import com.mtr.springbootactivemqdynamodb.service.DynamoDbOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private DynamoDbOperationService dbOperationService;

    @PostMapping("/product")
    public Product saveMovie(@RequestBody Product productDetails) {
        return dbOperationService.saveData(productDetails);
    }

    @PutMapping("/product")
    public Product updateMovie(@RequestBody Product productDetails) {
        return dbOperationService.updateData(productDetails);
    }

    @GetMapping("/product/{id}")
    public Product findById(@PathVariable("id") String id) {
        return dbOperationService.findById(id);
    }

    @DeleteMapping("/product/{id}")
    public void deleteById(@PathVariable("id") String id) {
        dbOperationService.deleteById(id);
    }

    @GetMapping("/product")
    public List<Product> getMovieListByGenre(@RequestParam("genre") String genre) {
        return dbOperationService.scanDataByGenre(genre);
    }
}
