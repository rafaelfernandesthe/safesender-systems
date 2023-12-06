//package br.com.rti.service;
//
//import java.io.File;
//import java.security.SecureRandom;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.apache.camel.Produce;
//import org.apache.camel.ProducerTemplate;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//
//import br.com.rti.config.RabbitMQConfig;
//import br.com.rti.rest.dto.MailSenderRequestDto;
//import br.com.rti.rest.dto.MailSenderResponseDto;
//import br.com.rti.rest.dto.MailSentResponseDto;
//import br.com.rti.routes.RabbitMQProducer;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Service
//public class MailSenderToQueueRabbitQMService {
//
//	@Autowired
//	private RabbitMQProducer rabbitMqProducer;
//
//	private static final SecureRandom RANDOM = new SecureRandom();
//	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
//	private static final Integer HASH_SIZE = 21;
//
//	public MailSentResponseDto sendMailToQueue(MailSenderRequestDto emailRequest) throws Exception {
//
//		List<MailSenderResponseDto> responseList = new ArrayList<>();
//
//		boolean certSignedMail = !ObjectUtils.isEmpty(emailRequest.getCertificate());
//
//		for (String emailTo : emailRequest.getToMail().split(";")) {
//
//			MailSenderRequestDto emailIn = new MailSenderRequestDto();
//
//			BeanUtils.copyProperties(emailRequest, emailIn);
//
//			String emailHashId = getHash();
//			emailIn.setInternalid(emailHashId + "@" + emailRequest.getSender());
//			emailIn.setToMail(emailTo);
//
//			if (ObjectUtils.isEmpty(emailIn.getUniqueid())) {
//				emailIn.setUniqueid(emailIn.getInternalid());
//			}
//
//			if (certSignedMail) {
//				File certificado = new File("caminho/para/certificado.p12");
//				if (!certificado.exists()) {
//					log.error("CERTIFICADO INVALIDO");
//				}
//			}
//
//			rabbitMqProducer.enviarMensagem(RabbitMQConfig.TO_SAVE_QUEUE, emailIn);
//			
//			responseList.add(new MailSenderResponseDto(emailIn.getUniqueid(), emailIn.getFromName(),
//					emailIn.getFromMail(), emailIn.getToMail(), emailIn.getSubject(), emailIn.getAttachments().size()));
//		}
//
//		return new MailSentResponseDto(responseList);
//	}
//
//	private static String getHash() {
//		StringBuilder sb = new StringBuilder();
//		sb.append(LocalDateTime.now().format(DATETIME_FORMATTER));
//
//		while (sb.length() < HASH_SIZE) {
//			int randomInt = RANDOM.nextInt(36);
//			char randomChar = (char) (randomInt < 10 ? '0' + randomInt : 'a' + randomInt - 10);
//			sb.append(randomChar);
//		}
//
//		return sb.toString();
//	}
//
//}
