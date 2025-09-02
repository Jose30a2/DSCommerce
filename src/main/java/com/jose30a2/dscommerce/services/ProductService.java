package com.jose30a2.dscommerce.services;


import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.jose30a2.dscommerce.dto.ProductDTO;
import com.jose30a2.dscommerce.entities.Product;
import com.jose30a2.dscommerce.repositories.ProductRepository;
import com.jose30a2.dscommerce.services.exceptions.DatabaseException;
import com.jose30a2.dscommerce.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
        return new ProductDTO(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso nao encontrado")));
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable){
        Page<Product> result = repository.findAll(pageable);
        return result.map( x -> new ProductDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO product){
        Product entity = new Product();
        copyDtoToEntity(product, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        try {
            Product entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }catch(EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso nao encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(long id){
        if(!repository.existsById(id)){
            throw new ResourceNotFoundException("Recurso nao encontrado");
        }
        try {
            repository.deleteById(id);
        }catch(DataIntegrityViolationException e){
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ProductDTO product, Product entity){
        entity.setName(product.getName());
        entity.setPrice(product.getPrice());
        entity.setDescription(product.getDescription());
        entity.setImgUrl(product.getImgUrl());
    }
}
