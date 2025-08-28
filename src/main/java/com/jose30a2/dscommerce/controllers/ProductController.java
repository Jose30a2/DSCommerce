package com.jose30a2.dscommerce.controllers;

import com.jose30a2.dscommerce.dto.ProductDTO;
import com.jose30a2.dscommerce.entities.Product;

import com.jose30a2.dscommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value="/products")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping(value = "/{id}")
    public ProductDTO findById(@PathVariable long id){
        return service.findById(id);

    }
}
