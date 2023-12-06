package br.com.rti.domain.dtos.restapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude( JsonInclude.Include.NON_EMPTY )
public class MailSenderResponseDto {

	private String uniqueid;

	@JsonProperty( value = "from_name" )
	private String fromName;

	@JsonProperty( value = "from_mail" )
	private String fromMail;

	@JsonProperty( value = "to_mail" )
	private String toMail;

	private String subject;

	private int attachments;

}
