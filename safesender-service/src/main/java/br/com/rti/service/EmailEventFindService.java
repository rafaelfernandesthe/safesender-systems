package br.com.rti.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rti.domain.dtos.frontend.EmailEventEntityDto;
import br.com.rti.domain.entities.EmailEventEntity;
import br.com.rti.domain.repositories.EmailEventEntityRepository;
import lombok.extern.log4j.Log4j2;

@Transactional(readOnly = true)
@Log4j2
@Service
public class EmailEventFindService {

	@Autowired
	private EmailEventEntityRepository emailEventEntityRepository;

	public EmailEventEntityDto findOneByInternalidOrderByEventDateDesc(String internalid) throws Exception {
		log.info("findOneByInternalidOrderByEventDateDesc {}", internalid);
		return entityToDtoConverter(
				emailEventEntityRepository.findOneByInternalidOrderByEventDateDesc(internalid).orElse(null));
	}

	public List<EmailEventEntityDto> findAllByInternalidOrderByEventDateDesc(String internalid) throws Exception {
		log.info("findAllByInternalidOrderByEventDateDesc {}", internalid);
		List<EmailEventEntity> result = emailEventEntityRepository.findByInternalidOrderByEventDateDesc(internalid);

		return result.stream().map(this::entityToDtoConverter).collect(Collectors.toList());
	}

	private EmailEventEntityDto entityToDtoConverter(EmailEventEntity entity) {
		return new EmailEventEntityDto(entity);
	}
}
