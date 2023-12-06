package br.com.rti.apiwebhookreceiver.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/healthy" )
public class HealthyController {

	@Value( "${info.app.version}" )
	private String version;

	@Value( "${info.app.build}" )
	private String build;

	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE )
	public String healthyStatus() {
		return String.format( "{\"healthy\":true,\"message\":\"OK\",\"version\":\"%s\",\"build\":\"%s\"}", version, build );
	}
}
