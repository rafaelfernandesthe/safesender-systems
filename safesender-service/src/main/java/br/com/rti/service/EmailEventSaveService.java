package br.com.rti.service;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rti.domain.dtos.restapi.EmailEventPmtaDto;
import br.com.rti.domain.entities.EmailEventEntity;
import br.com.rti.domain.entities.SenderEntity;
import br.com.rti.domain.enums.EmailEventStatus;
import br.com.rti.domain.enums.EmailEventType;
import br.com.rti.domain.repositories.EmailEventEntityRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class EmailEventSaveService {

	private static final String OPEN = "open";
	private static final String DISCARD = "discard";

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

	@Autowired
	private CacheService cacheService;

	@Autowired
	private EmailEventEntityRepository emailEventEntityRepository;
	
	public void saveMailEventEntity(EmailEventEntity eventRequest) throws Exception {
		this.saveMailEventEntityList(Arrays.asList(eventRequest));
	}
	
	public void saveMailEventEntityList(List<EmailEventEntity> eventRequestList) throws Exception {
		emailEventEntityRepository.saveAll(eventRequestList);
	}

	public void saveMailEvent(EmailEventPmtaDto eventRequest) throws Exception {
		saveMailEventList(Arrays.asList(eventRequest));
	}

	public List<EmailEventEntity> saveMailEventList(List<EmailEventPmtaDto> eventRequestList) throws Exception {

		LocalDateTime start = LocalDateTime.now();
		log.info("INICIO saveMailEvent LOOP - {} ", start);

		List<EmailEventEntity> eventPersistentList = new ArrayList<>();

		for (EmailEventPmtaDto eventRequest : eventRequestList) {

			EmailEventEntity persistenceRequest = new EmailEventEntity();

			persistenceRequest.setEventDate(sdf.parse(eventRequest.getTimeLogged()));
			persistenceRequest.setStatus(EmailEventStatus.NEW);
			persistenceRequest.setFromMail(eventRequest.getOrig());
			persistenceRequest.setToMail(eventRequest.getRcpt());
			persistenceRequest.setTryCount(0);
			persistenceRequest.setEventType(getEventType(eventRequest.getType(), eventRequest.getDlvType()));
			persistenceRequest.setInternalid(eventRequest.getEnvId());
			String[] envid = eventRequest.getEnvId().split("@");
			persistenceRequest.setUniqueid(eventRequest.getEnvId());
			persistenceRequest.setSender(getSender(envid[1]));
			if (envid.length == 3) {
				persistenceRequest.setUniqueid(envid[0]);
				persistenceRequest.setSender(getSender(envid[2]));
			}
			persistenceRequest.setResponse(eventRequest.getDsnStatus());
			persistenceRequest.setResponseDialg(eventRequest.getDsnDiag());

			eventPersistentList.add(persistenceRequest);
		}

		emailEventEntityRepository.saveAll(eventPersistentList);

		LocalDateTime end = LocalDateTime.now();
		log.info("FIM saveMailEvent LOOP - {} - totalTime(milli): {} - totalTime(seconds): {}", end,
				Duration.between(start, end).toMillis(), Duration.between(start, end).toSeconds());
		
		return eventPersistentList;
	}

	private EmailEventType getEventType(String type, String dlvType) {
		/*
		 PowerMTA writes various accounting records for every recipient. 
		 The various record types can be stored all in one file, or in separate files by record type. 
		 The record types are “d” for delivered, “b” for bounced, “t” for transient, “tq” for transient-queue, 
		 “r” for received, "f" for feedback loop records and "rb"/"rs" for remote bounce or remote status records. 
		 By default, only “d” and “b” records are recorded.
		*/
		
		if(DISCARD.equals(dlvType)) {
			return EmailEventType.DIS; 
		}
		
		if(OPEN.equals(dlvType)) {
			return EmailEventType.OPE; 
		}
				
		switch (type) {
			case "t":
				return EmailEventType.ERR;
			case "r":
				return EmailEventType.PRO;
			case "d":
				return EmailEventType.DEL;
			case "b":
				return EmailEventType.BOU;
		}
		return null;
	}

	private SenderEntity getSender(String senderCode) {
		return cacheService.getSenderList().parallelStream().filter(s -> s.getCode().equals(senderCode)).findFirst()
				.orElse(null);
	}

}
