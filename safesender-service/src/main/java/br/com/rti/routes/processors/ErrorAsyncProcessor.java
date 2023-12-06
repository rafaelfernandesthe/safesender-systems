package br.com.rti.routes.processors;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class ErrorAsyncProcessor implements Processor {

	@SuppressWarnings("unchecked")
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Object b = exchange.getIn().getBody();
				
		List<MailSenderRequestDto> mensagens = new ArrayList<>();
		if(b instanceof List) {
			mensagens = exchange.getIn().getBody(List.class);
		} else {
			mensagens.add(exchange.getIn().getBody(MailSenderRequestDto.class));
		}
		
		mensagens.forEach(m -> {
			log.error("MENSAGEM COM ERRO - sender:{} uniqueid:{}", m.getSender(),
					m.getUniqueid());
		});
		
		log.error("Falha ao processar mensagem - size:" + mensagens.size(),
				exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));

	}

}