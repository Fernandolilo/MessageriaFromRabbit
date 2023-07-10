package com.systempro.product.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.systempro.product.domain.data.CategoryVO;
import com.systempro.product.domain.data.ProductVO;



@Component
public class EstoqueSendMessage {

	public static final String ESTOQUE_EXCHANGE = "estoque.exchange";

	public static final String ROUTING_kEY = "estoque.#";
	

	public final RabbitTemplate rabbitTemplate;

	public EstoqueSendMessage(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessageCategory(CategoryVO categoryVO) {
		rabbitTemplate.convertAndSend(ESTOQUE_EXCHANGE, ROUTING_kEY, categoryVO);
	}
	
	public void sendMessageProd(ProductVO productVO) {
		rabbitTemplate.convertAndSend(ESTOQUE_EXCHANGE, ROUTING_kEY, productVO);
	}

	


}