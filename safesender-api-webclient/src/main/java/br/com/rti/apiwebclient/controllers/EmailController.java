package br.com.rti.apiwebclient.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rti.domain.dtos.frontend.EmailEntityDto;
import br.com.rti.service.EmailFindService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/email")
public class EmailController {

	@Autowired
	public EmailFindService emailService;

	@GetMapping("/all/{senderCode}")
	public Page<EmailEntityDto> findAll(
			@PathVariable(value="senderCode", required = true) String senderCode,
			@RequestParam(defaultValue = "0") int pageNumber,
			@RequestParam(defaultValue = "10") int pageSize) throws Exception {
		log.info("EmailController.findAll(senderCode:{}, pageNumber:{}, pageSize:{}) /all", senderCode, pageNumber, pageSize);
		return emailService.findAll(senderCode, pageNumber, pageSize);
	}

	@GetMapping("/one/{senderCode}/{id}")
	public ResponseEntity<EmailEntityDto> findOne(
			@PathVariable(value="senderCode", required = true) String senderCode,
			@PathVariable("id") Long id) throws Exception {
		log.info("EmailController.findOne /one/{}", id);
		return ResponseEntity.ok(emailService.findById(senderCode, id));
	}

}
