package com.systempro.product.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.systempro.product.domain.Product;
import com.systempro.product.domain.data.ProductVO;
import com.systempro.product.message.EstoqueSendMessage;
import com.systempro.product.repositories.ProductRepository;
import com.systempro.product.services.exceptions.ObjectNotFoundException;

@Service
public class ProductService {

	private final ProductRepository repository;
	private final EstoqueSendMessage productSendMessage;

	public ProductService(ProductRepository repository, EstoqueSendMessage productSendMessage) {
		this.repository = repository;
		this.productSendMessage = productSendMessage;
	}

	public Page<ProductVO> findAllPage(Pageable pageable) {
		Page<Product> page = repository.findAll(pageable);
		return page.map(this::convertedProductVO);
	}

	private ProductVO convertedProductVO(Product category) {
		return new ProductVO().create(category);
	}

	public ProductVO findById(Long id) {
		Product category = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Categoria não cadastrada"));
		return ProductVO.create(category);
	}

	public ProductVO create(ProductVO productVo) {
		if (productVo.getId() == null) {
			ProductVO categoria = ProductVO.create(repository.save(Product.create(productVo)));
			productSendMessage.sendMessageProd(productVo);
			return categoria;
		}
		return null;
	}

	public void delete(ProductVO productVo) {
		final Optional<Product> optionalProduct = repository.findById(productVo.getId());
		if (!optionalProduct.isPresent()) {
			throw new ObjectNotFoundException("Passe um identificador válido: ");
		}

		repository.deleteById(productVo.getId());
		// productSendMessage.sendMessageProduct(productVo);
	}

	public ProductVO update(ProductVO productVo) {
		final Optional<Product> optionalProduct = repository.findById(productVo.getId());

		if (!optionalProduct.isPresent()) {
			throw new ObjectNotFoundException("Passe um identificador válido: ");
		}
		// productSendMessage.sendMessageProduct(productVo);
		return ProductVO.create(repository.save(Product.create(productVo)));
	}
}
