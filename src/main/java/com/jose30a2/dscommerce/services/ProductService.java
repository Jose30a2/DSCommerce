package com.jose30a2.dscommerce.services;

import com.jose30a2.dscommerce.dto.ProductDTO;
import com.jose30a2.dscommerce.entities.Product;
import com.jose30a2.dscommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public ProductDTO findById(long id){
        /**Optional<Product> result = repository.findById(id);
        Product product = result.get();
        return new ProductDTO(product);**/
        return new ProductDTO(repository.findById(id).get());
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = repository.findAll(pageable);
        return result.map( x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO product){
        Product entity = new Product();
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setDescription(product.getDescription());
        entity.setImgUrl(product.getImgUrl());

        entity = repository.save(entity);
        return new ProductDTO(entity);
    }
}
