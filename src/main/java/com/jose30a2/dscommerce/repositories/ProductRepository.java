package com.jose30a2.dscommerce.repositories;

import com.jose30a2.dscommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
