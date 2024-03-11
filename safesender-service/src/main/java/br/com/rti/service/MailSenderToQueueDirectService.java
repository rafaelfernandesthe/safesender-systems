package br.com.rti.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import br.com.rti.domain.dtos.restapi.MailSenderAttachmentRequestDto;
import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import br.com.rti.domain.dtos.restapi.MailSenderResponseDto;
import br.com.rti.domain.dtos.restapi.SenderEntityDto;
import br.com.rti.domain.entities.SenderEntity;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MailSenderToQueueDirectService {

	@Produce("direct:emailToSaveAndSend")
	private ProducerTemplate producerTemplate;
	
	@Autowired
	private CacheService cacheService;

	public MailSenderResponseDto sendMailToQueue(MailSenderRequestDto emailRequest) throws Exception {

		validateTokenAndSenderid(emailRequest);
		
		boolean certSignedMail = !ObjectUtils.isEmpty(emailRequest.getCertificate());

		MailSenderRequestDto emailIn = new MailSenderRequestDto();

		BeanUtils.copyProperties(emailRequest, emailIn);

		if (certSignedMail) {
			File certificado = new File("caminho/para/certificado.p12");
			if (!certificado.exists()) {
				log.error("CERTIFICADO INVALIDO");
			}
		}
		
		producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), emailIn);

		List<MailSenderAttachmentRequestDto> attachments = emailIn.getAttachments();
		attachments = attachments == null ? new ArrayList<>() : attachments;
		
		return new MailSenderResponseDto(emailIn.getUniqueid(), emailIn.getFromName(),
				emailIn.getFromMail(), emailIn.getToMail(), emailIn.getSubject(), attachments.size());

	}

	private void validateTokenAndSenderid(MailSenderRequestDto emailRequest) throws Exception {
		Optional<SenderEntity> senderOpt = cacheService.getSenderList().stream().filter(s -> s.getToken().equals(emailRequest.getToken())).findFirst();
		if(!senderOpt.isPresent()) {
			throw new Exception("Invalid Token");
		}
		SenderEntity sender = senderOpt.get();
		if(!sender.getCode().equals(emailRequest.getSender())) {
			throw new Exception("Invalid Sender for Token");
		}
		
		SenderEntityDto senderDto = new SenderEntityDto();
		BeanUtils.copyProperties(sender, senderDto);
		senderDto.setClientId(sender.getClient().getId());
		emailRequest.setSenderObj(senderDto);
	}

}
