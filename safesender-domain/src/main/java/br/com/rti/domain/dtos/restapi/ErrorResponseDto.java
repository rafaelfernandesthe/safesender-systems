package br.com.rti.domain.dtos.restapi;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

	// http status
	private int status;
	// personalized code
	private String errorCode;
	// message
	private String message;
	// error list
	private Map<String, String> errors;

}