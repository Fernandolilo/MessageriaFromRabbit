package com.systempro.product.message;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.systempro.product.domain.data.CategoryVO;

@Component
public class ProductSendMessage {

	@Value("${product.rabbitmq.exchange}")
	String exchange;

	@Value("${product.rabbitmq.routingkey}")
	String routingkey;

	public final RabbitTemplate rabbitTemplate;

	public ProductSendMessage(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	public void sendMessage(CategoryVO categoryVO) {
		rabbitTemplate.convertAndSend(exchange, routingkey, categoryVO);
	}

}