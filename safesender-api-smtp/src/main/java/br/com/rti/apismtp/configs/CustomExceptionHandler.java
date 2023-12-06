package br.com.rti.apismtp.configs;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.rti.domain.dtos.restapi.ErrorResponseDto;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class CustomExceptionHandler {

	@ExceptionHandler( Exception.class )
	public ResponseEntity<ErrorResponseDto> handleException( Exception ex ) {
		log.error("handleException", ex);
		return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( ErrorResponseDto.builder().status( HttpStatus.INTERNAL_SERVER_ERROR.value() ).message( ex.getMessage() ).errorCode( null ).build() );
	}

	@ExceptionHandler( MethodArgumentNotValidException.class )
	public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValidException( MethodArgumentNotValidException ex ) {
		log.error("handleMethodArgumentNotValidException", ex);
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors().forEach( error -> {
			String fieldName = error.getField();
			String errorMessage = error.getDefaultMessage();
			errors.put( fieldName, errorMessage );
		} );

		return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( ErrorResponseDto.builder().status( HttpStatus.BAD_REQUEST.value() ).message( "Validation Error" ).errors( errors ).errorCode( null ).build() );
	}

}