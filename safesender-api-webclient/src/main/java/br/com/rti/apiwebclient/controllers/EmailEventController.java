package br.com.rti.apiwebclient.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rti.domain.dtos.frontend.EmailEventEntityDto;
import br.com.rti.service.EmailEventFindService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/event")
public class EmailEventController {

	@Autowired
	public EmailEventFindService emailEventFindService;

	@GetMapping("/one/last/byInternalid/{internalid}")
	public EmailEventEntityDto findOneLastByInternalid(@PathVariable("internalid") String internalid) throws Exception {
		log.info("EventController.findOneLastByInternalid /one/last/byInternalid/{}", internalid);
		return emailEventFindService.findOneByInternalidOrderByEventDateDesc(internalid);
	}

	@GetMapping("/all/byInternalid/{internalid}")
	public List<EmailEventEntityDto> findAllByInternalid(@PathVariable("internalid") String internalid)
			throws Exception {
		log.info("EventController.findAllByInternalid /one/last/byInternalid/{}", internalid);
		return emailEventFindService.findAllByInternalidOrderByEventDateDesc(internalid);
	}

}
