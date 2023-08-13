package com.systempro.sales.message;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.systempro.sales.domain.Category;
import com.systempro.sales.domain.Product;
import com.systempro.sales.domain.data.ProductVO;
import com.systempro.sales.domain.data.ProductVO;
import com.systempro.sales.repositories.ProductRepository;

@Component
public class ReceiveMessageProducts {
	private final ProductRepository repository;

	@Autowired
	public ReceiveMessageProducts(ProductRepository repository) {
		this.repository = repository;
	}

	@RabbitListener(queues = MessageConfig.ESTOQUE_QUEUE_PRODUCT)
	public void receive(@Payload ProductVO product) {

		if (product.getName() == null) {
			delete(product);
		} else {

			create(product);
		}
	}

	public ProductVO create(ProductVO product) {
		product.setId(product.getId());
		repository.save(Product.create(product));
		return product;
	}

	public void delete(ProductVO product) {
		Optional<Product> prod = repository.findById(product.getId());
		repository.deleteById(product.getId());

	}

}
