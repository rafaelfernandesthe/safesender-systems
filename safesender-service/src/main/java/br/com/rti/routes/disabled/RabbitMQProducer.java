package br.com.rti.routes.disabled;
//package br.com.rti.routes;
//
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import br.com.rti.rest.dto.MailSenderRequestDto;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Component
//public class RabbitMQProducer {
//
//	@Autowired
//	private RabbitTemplate rabbitTemplate;
//
//	public void enviarMensagem(String nomeFila, MailSenderRequestDto mensagem) {
//		rabbitTemplate.convertAndSend(nomeFila, mensagem);
//
//		log.info("Mensagem enviada para Fila {}: sender:{} uniqueid:{}", nomeFila, mensagem.getSender(),
//				mensagem.getUniqueid());
//	}
//}