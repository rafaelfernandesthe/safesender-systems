package br.com.rti.apiwebhookreceiver.processors;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.rti.domain.dtos.restapi.EmailEventEntityWebhookDto;
import br.com.rti.domain.entities.EmailEventEntity;
import br.com.rti.domain.enums.EmailEventStatus;
import br.com.rti.service.EmailEventSaveService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class EmailEventSendToWebhookUrlProcessor implements Processor {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private EmailEventSaveService emailEventSaveService;
	
	public void process(Exchange exchange) throws Exception {
		log.info("INICIO - EmailEventSendToWebhookUrlProcessor.process()");

		@SuppressWarnings("unchecked")
		List<EmailEventEntity> mensagens = exchange.getIn().getBody(List.class);

		log.info("INICIO - EmailEventSendToWebhookUrlProcessor {} mensagens", mensagens.size());

		 Map<String, List<EmailEventEntityWebhookDto>> webhookBySender = mensagens.stream()
				 	.map(webhook -> {
				 		EmailEventEntityWebhookDto webhookDto = new EmailEventEntityWebhookDto();
						BeanUtils.copyProperties(webhook, webhookDto);
						webhookDto.setSender(webhook.getSender().getCode());
						webhookDto.setSenderWebhookUrl(webhook.getSender().getWebhookUrl());
						webhookDto.setStatus(null);
						webhookDto.setTryCount(webhookDto.getTryCount()+1);
						return webhookDto;
				 	}).collect(Collectors.groupingBy(EmailEventEntityWebhookDto::getSenderWebhookUrl));
		
		 webhookBySender.forEach((senderWebhookUrl, eventList) -> {
        	restTemplate.postForLocation(senderWebhookUrl, eventList);
		});
		 
		 
		 mensagens.forEach(m -> {
			 m.setStatus(EmailEventStatus.SEN);
			 m.setTryCount(1);
		 });
		 
		 emailEventSaveService.saveMailEventEntityList(mensagens);
		
		log.info("FIM - EmailEventSendToWebhookUrlProcessor {} mensagens", mensagens.size());

	}
	
}