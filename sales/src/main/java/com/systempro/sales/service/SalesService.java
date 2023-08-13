package com.systempro.sales.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.systempro.sales.domain.ItemSales;
import com.systempro.sales.domain.Product;
import com.systempro.sales.domain.Sales;
import com.systempro.sales.domain.data.SalesVO;
import com.systempro.sales.repositories.ItemRepository;
import com.systempro.sales.repositories.ProductRepository;
import com.systempro.sales.repositories.SalesRepository;
import com.systempro.sales.service.exceptions.ObjectNotFoundException;

@Service
public class SalesService {

	private final SalesRepository repository;
	private final ItemRepository itemRepository;
	private final ProductRepository productRepository;
	private String format;

	public SalesService(SalesRepository repository, ItemRepository itemRepository,
			ProductRepository productRepository) {
		this.repository = repository;
		this.itemRepository = itemRepository;
		this.productRepository = productRepository;
	}

	private SalesVO convertedSalesVO(Sales sales) {
		return new SalesVO().create(sales);
	}

	private Sales convertedSales(SalesVO sales) {
		return new Sales().create(sales);
	}

	public SalesVO create(SalesVO salesVO) {
		SalesVO sales = SalesVO.create(repository.save(Sales.create(salesVO)));
		Sales sa = new Sales(sales.getId(), sales.getInstante());

		var sa_id = sa.getId();
		for (ItemSales ip : salesVO.getItens()) {
			ip.setProduct(ip.getProduct());
			ip.setName(ip.getName());
			ip.setAmount(ip.getAmount());
			ip.setPrice(ip.getPrice());
			ip.setSales(sa);

			if (!productRepository.existsById(ip.getProduct().getId())) {
				repository.deleteById(sa_id);
			}

			// update quantity when placing a new order
			if (productRepository.existsById(ip.getProduct().getId())) {
				Optional<Product> product = productRepository.findById(ip.getProduct().getId());
				if (product.isPresent()) {
					product.get().getValue(ip.getAmount());
				}

			}

			itemRepository.save(ip);
		}

		return sales;
	}

	public SalesVO findById(Long id) {
		Sales sales = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pedido não existe"));
		return SalesVO.create(sales);
	}

	public List<Sales> findBySalesDate(LocalDateTime instante, LocalDateTime termino) {
		List<Sales> sales = repository.findByInstanteBetween(instante, termino);		
		return sales;
	}

}
