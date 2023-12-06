package br.com.rti.apismtp.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import br.com.rti.service.MailSaveService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class MailSaveBatchProcessor implements Processor {

	@Autowired
	private MailSaveService mailSaveService;

	public void process(Exchange exchange) throws Exception {
		log.info("INICIO - MailSaveProcessor.process()");

		@SuppressWarnings("unchecked")
		List<MailSenderRequestDto> mensagens = exchange.getIn().getBody(List.class);

		log.info("INICIO - MailSaveProcessor {} mensagens", mensagens.size());

		mailSaveService.saveMail(mensagens);

		log.info("FIM - MailSaveProcessor {} mensagens", mensagens.size());

	}
}