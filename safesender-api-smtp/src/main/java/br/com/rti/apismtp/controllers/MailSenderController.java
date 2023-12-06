package br.com.rti.apismtp.controllers;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rti.apismtp.services.MailSenderSpringService;
import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import br.com.rti.domain.dtos.restapi.MailSenderResponseDto;
import br.com.rti.service.CacheService;
import br.com.rti.service.MailSenderToQueueDirectService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/sendmail")
public class MailSenderController {
	
	private static final String HEADER_API_SENDER = "api-sender";
	private static final String HEADER_API_TOKEN = "api-token";
	private static final SecureRandom RANDOM = new SecureRandom();
	private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("YYYYMMddHHmmssSSS");
	private static final Integer HASH_SIZE = 21;

	@Autowired
	private MailSenderToQueueDirectService mailSenderToQueueService;

	@Autowired
	public MailSenderSpringService mailSenderService;

	@Autowired
	public CacheService cacheService;
	
	@PostMapping("/one")
	public ResponseEntity<MailSenderResponseDto> sendOne(@Validated @RequestBody MailSenderRequestDto emailRequest,
			HttpServletRequest request) throws Exception {

		String sender = getSenderCode(request.getHeader(HEADER_API_SENDER));
		String token = getSenderToken(request.getHeader(HEADER_API_TOKEN));
		String uniqueid = emailRequest.getUniqueid();
		boolean hasUniqueid = !ObjectUtils.isEmpty(uniqueid);
		
		String emailHashId = getHash();
		
		if (hasUniqueid) {
			emailRequest.setInternalid(uniqueid + "@" + emailHashId + "@" + sender);
		} else {
			emailRequest.setInternalid(emailHashId + "@" + sender);
			emailRequest.setUniqueid(emailRequest.getInternalid());
		}
		
		log.info("/one internalid:{} | uniqueid:{} | sender:{}", emailRequest.getInternalid(), uniqueid, sender);

		if (ObjectUtils.isEmpty(token) || ObjectUtils.isEmpty(sender)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN.value()).build();
		}

		emailRequest.setSender(sender);
		emailRequest.setToken(token);
		return ResponseEntity.ok(mailSenderToQueueService.sendMailToQueue(emailRequest));
	}

	private String getSenderToken(String token) {
		if (cacheService.getSenderTokenList().contains(token)) {
			return token;
		}
		return null;
	}

	private String getSenderCode(String sender) {
		if (cacheService.getSenderCodeList().contains(sender)) {
			return sender;
		}
		return null;
	}
	
	private static String getHash() {
		StringBuilder sb = new StringBuilder();
		sb.append(LocalDateTime.now().format(DATETIME_FORMATTER));

		while (sb.length() < HASH_SIZE) {
			int randomInt = RANDOM.nextInt(36);
			char randomChar = (char) (randomInt < 10 ? '0' + randomInt : 'a' + randomInt - 10);
			sb.append(randomChar);
		}

		return sb.toString();
	}

}
