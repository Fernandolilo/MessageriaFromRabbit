package com.systempro.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempro.product.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
