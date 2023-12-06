package br.com.rti.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import br.com.rti.domain.entities.EmailAttachmentEntity;
import br.com.rti.domain.entities.EmailContentEntity;
import br.com.rti.domain.entities.EmailEntity;
import br.com.rti.domain.entities.SenderEntity;
import br.com.rti.domain.repositories.EmailEntityRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MailSaveService {
	
	@Autowired
	private EmailEntityRepository emailEntityRepository;
	
	public void saveMail(MailSenderRequestDto emailRequest) throws Exception {
		saveMail(Arrays.asList(emailRequest));
	}

	public void saveMail(List<MailSenderRequestDto> emailRequestList) throws Exception {
		
		LocalDateTime start = LocalDateTime.now();
		log.info("INICIO saveMail LOOP - {} ", start);
		
		List<EmailEntity> emailRequestPersistentList = new ArrayList<>();

		for(MailSenderRequestDto emailRequest : emailRequestList) {
		
			EmailEntity persistenceRequest = new EmailEntity();
			BeanUtils.copyProperties(emailRequest, persistenceRequest, "id");
			
			persistenceRequest.setRequestDate(new Date());
			SenderEntity sender = new SenderEntity();
			BeanUtils.copyProperties(emailRequest.getSenderObj(), sender);
			persistenceRequest.setSender(sender);
			persistenceRequest.setClientid(emailRequest.getSenderObj().getClientId());
	
			EmailContentEntity emailContent = new EmailContentEntity();
			emailContent.setEmail(persistenceRequest);
			emailContent.setHtmlContent(emailRequest.getHtml());
			emailContent.setTextContent(emailRequest.getText());
			persistenceRequest.setEmailContent(emailContent);
	
			List<EmailAttachmentEntity> attachments = new ArrayList<>();
			emailRequest.getAttachments().forEach(a -> {
				EmailAttachmentEntity item = new EmailAttachmentEntity();
				item.setEmail(persistenceRequest);
				item.setFileName(a.getFileName());
				item.setFileContent(a.getFileContentBase64().getBytes());
				attachments.add(item);
			});
			persistenceRequest.setEmailAttachments(attachments);
			persistenceRequest.setAttachments(attachments.size());
			
			emailRequestPersistentList.add(persistenceRequest);
		}

		emailEntityRepository.saveAll(emailRequestPersistentList);
		
		LocalDateTime end = LocalDateTime.now();
		log.info("FIM saveMail LOOP - {} - totalTime(milli): {} - totalTime(seconds): {}", end, Duration.between(start, end).toMillis(), Duration.between(start, end).toSeconds());
	}

}
