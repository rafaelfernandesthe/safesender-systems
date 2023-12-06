//package br.com.rti.routes.processors;
//
//import java.util.List;
//
//import org.apache.camel.Exchange;
//import org.apache.camel.Processor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import br.com.rti.domain.dtos.MailSenderRequestDto;
//import br.com.rti.service.MailSenderJavaxService;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Component
//public class MensagemSenderBatchJavaxProcessor implements Processor {
//
//	@Autowired
//	private MailSenderJavaxService mailSenderJavaxService;
//
//	public void process(Exchange exchange) throws Exception {
//		log.info("INICIO - MensagemBatchSenderJavaxProcessor.process()");
//
//		@SuppressWarnings("unchecked")
//		List<MailSenderRequestDto> mensagens = exchange.getIn().getBody(List.class);
//
//		log.info("INICIO - mailSenderJavaxService {} mensagens", mensagens.size());
//
//		mailSenderJavaxService.sendMail(mensagens);
//
//		log.info("FIM - mailSenderJavaxService {} mensagens", mensagens.size());
//
//	}
//}