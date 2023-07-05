package com.systempro.product.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.systempro.product.domain.Category;
import com.systempro.product.domain.data.CategoryVO;
import com.systempro.product.message.ProductSendMessage;
import com.systempro.product.repositories.CategoryRepository;
import com.systempro.product.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	private final CategoryRepository repository;
	private final ProductSendMessage productSendMessage;

	public CategoryService(CategoryRepository repository, ProductSendMessage productSendMessage) {
		this.repository = repository;
		this.productSendMessage = productSendMessage;
	}

	public Page<CategoryVO> findAllPage(Pageable pageable) {
		Page<Category> page = repository.findAll(pageable);
		return page.map(this::convertedCategoryVO);
	}

	private CategoryVO convertedCategoryVO(Category category) {
		return new CategoryVO().create(category);
	}

	public CategoryVO findById(Long id) {
		Category category = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Categoria não cadastrada"));
		return CategoryVO.create(category);
	}

	public CategoryVO create(CategoryVO categoryVO) {
		if (categoryVO.getId() == null) {
			CategoryVO categoria = CategoryVO.create(repository.save(Category.create(categoryVO)));
			productSendMessage.sendMessage(categoryVO);
			return categoria;
		}
		return null;
	}

	public void delete(CategoryVO categoryVO) {

		final Optional<Category> optionalCategory = repository.findById(categoryVO.getId());

		if (!optionalCategory.isPresent()) {
			throw new ObjectNotFoundException("Passe um identificador válido: ");
		}

		repository.deleteById(categoryVO.getId());
		productSendMessage.sendMessage(categoryVO);
	}

	public CategoryVO update(CategoryVO categoryVO) {
		final Optional<Category> optionalCategory = repository.findById(categoryVO.getId());

		if (!optionalCategory.isPresent()) {
			throw new ObjectNotFoundException("Passe um identificador válido: ");
		}
		productSendMessage.sendMessage(categoryVO);
		return CategoryVO.create(repository.save(Category.create(categoryVO)));
	}
}
