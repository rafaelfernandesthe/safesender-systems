package br.com.rti.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rti.domain.dtos.frontend.EmailEntityDto;
import br.com.rti.domain.entities.EmailEntity;
import br.com.rti.domain.entities.SenderEntity;
import br.com.rti.domain.repositories.EmailEntityRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@Transactional(readOnly = true)
public class EmailFindService {

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private EmailEntityRepository emailEntityRepository;

	public Page<EmailEntityDto> findAll(String senderCode, int pageNumber, int pageSize) throws Exception {
		log.info("MailService.findAll(senderCode:{}, pageNumber:{}, pageSize:{})", senderCode, pageNumber, pageSize);
		
		SenderEntity senderEntity = cacheService.getSenderBySendercode(senderCode);
		
		Page<EmailEntity> resultPage = emailEntityRepository.findBySender(senderEntity, PageRequest.of(pageNumber, pageSize));
		
		return entityPageToDtoConverter(resultPage);
	}

	public EmailEntityDto findById(String senderCode, Long id) {
		log.info("MailService.findById {}::{}", senderCode, id);
		
		SenderEntity senderEntity = cacheService.getSenderBySendercode(senderCode);
		
		return new EmailEntityDto(emailEntityRepository.findOneBySenderAndId(senderEntity, id).get());
	}
	
	private Page<EmailEntityDto> entityPageToDtoConverter(Page<EmailEntity> page) {
		
		List<EmailEntityDto> contentDto = page.get().map(this::entityToDtoConverter).collect(Collectors.toList());
		
		return new PageImpl<>(contentDto, page.getPageable(), page.getTotalElements());
    }
	
	private EmailEntityDto entityToDtoConverter(EmailEntity entity) {
        return new EmailEntityDto(entity);
    }

}
