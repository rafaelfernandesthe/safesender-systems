package br.com.rti.domain.dtos.restapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MailSenderAttachmentRequestDto {

	@JsonProperty( value = "file_name" )
	public String fileName;

	@JsonProperty( value = "file_contentBase64" )
	public String fileContentBase64;
}
