package com.systempro.sales.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.systempro.sales.domain.Sales;
import com.systempro.sales.domain.data.SalesVO;

@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

	@Query("SELECT t FROM Sales t WHERE t.instante BETWEEN :instante AND :termino")
	List<Sales> findByInstanteBetween(LocalDateTime instante, LocalDateTime termino);
	
}
