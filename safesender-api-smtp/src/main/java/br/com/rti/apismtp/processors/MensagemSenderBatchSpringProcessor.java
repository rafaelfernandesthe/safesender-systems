package br.com.rti.apismtp.processors;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rti.apismtp.services.MailSenderSpringService;
import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class MensagemSenderBatchSpringProcessor implements Processor {

	@Autowired
	private MailSenderSpringService mailSenderSpringService;

	public void process(Exchange exchange) throws Exception {
		log.info("INICIO - MensagemBatchSenderSpringProcessor.process()");

		@SuppressWarnings("unchecked")
		List<MailSenderRequestDto> mensagens = exchange.getIn().getBody(List.class);

		log.info("INICIO - mailSenderSpringService {} mensagens", mensagens.size());

		mailSenderSpringService.sendMail(mensagens);

		log.info("FIM - mailSenderSpringService {} mensagens", mensagens.size());

	}
}