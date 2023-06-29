package com.systempro.product.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.systempro.product.domain.Category;
import com.systempro.product.domain.data.CategoryVO;
import com.systempro.product.repositories.CategoryRepository;
import com.systempro.product.services.exceptions.ObjectNotFoundException;

@Service
public class CategoryService {

	private final CategoryRepository repository;

	public CategoryService(CategoryRepository repository) {
		this.repository = repository;
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
			CategoryVO tanque = CategoryVO.create(repository.save(Category.create(categoryVO)));
			return tanque;
		}if(categoryVO.getId() != null) {
			final Optional<Category> ca = repository.findById(categoryVO.getId());
			if(!ca.isPresent()) {
				throw new ObjectNotFoundException("Identificador não existe: Category invalido!");
			}return CategoryVO.create(repository.save(Category.create(categoryVO)));
		}
		return null;
	}

}
