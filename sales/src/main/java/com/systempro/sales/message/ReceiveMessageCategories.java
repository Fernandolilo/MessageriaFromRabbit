package com.systempro.sales.message;

import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.systempro.sales.domain.Category;
import com.systempro.sales.domain.data.CategoryVO;
import com.systempro.sales.repositories.CategoryRepository;
import com.systempro.sales.message.MessageConfig;

@Component
public class ReceiveMessageCategories {
	private final CategoryRepository repository;

	@Autowired
	public ReceiveMessageCategories(CategoryRepository repository) {
		this.repository = repository;
	}

	@RabbitListener(queues = MessageConfig.ESTOQUE_QUEUE_CATEGORY)
	public void receive(@Payload CategoryVO category) {
		if (category.getCategory() == null) {
			delete(category);
		} else {
			create(category);
		}
	}

	public CategoryVO create(CategoryVO category) {
		if (category.getId() != null) {
			category.setId(category.getId());
			category.setCategory(category.getCategory());
			repository.save(Category.create(category));
		}
		repository.save(Category.create(category));
		return category;
	}

	public void delete(CategoryVO category) {
		Optional<Category> cat = repository.findById(category.getId());
		if (cat.isPresent()) {
			repository.deleteById(category.getId());
		}
	}

}
