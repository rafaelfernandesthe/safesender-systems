package br.com.rti.apiwebhookreceiver.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rti.domain.dtos.restapi.EmailEventPmtaDto;
import br.com.rti.domain.entities.EmailEventEntity;
import br.com.rti.service.EmailEventSaveService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class EmailEventSaveBatchProcessor implements Processor {

	@Autowired
	private EmailEventSaveService emailEventSaveService;

	public void process(Exchange exchange) throws Exception {
		log.info("INICIO - MailSaveProcessor.process()");

		@SuppressWarnings("unchecked")
		List<EmailEventPmtaDto> mensagens = exchange.getIn().getBody(List.class);

		log.info("INICIO - MailSaveProcessor {} mensagens", mensagens.size());

		List<EmailEventEntity> result = emailEventSaveService.saveMailEventList(mensagens);

		log.info("FIM - MailSaveProcessor {} mensagens", result.size());
		
		exchange.getMessage().setBody(result);
	}
}