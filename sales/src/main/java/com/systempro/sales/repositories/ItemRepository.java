package com.systempro.sales.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systempro.sales.domain.ItemSales;

@Repository
public interface ItemRepository extends JpaRepository<ItemSales, Long>{

}
