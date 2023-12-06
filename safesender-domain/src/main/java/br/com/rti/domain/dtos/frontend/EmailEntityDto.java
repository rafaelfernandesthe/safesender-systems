package br.com.rti.domain.dtos.frontend;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.rti.domain.configs.CustomDateDeserializer;
import br.com.rti.domain.configs.CustomDateSerializer;
import br.com.rti.domain.entities.EmailEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailEntityDto {

	private Long id;

	@JsonProperty("request_date")
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonDeserialize(using = CustomDateDeserializer.class)
	private Date requestDate;

	private Long clientid;

	private String uniqueid;

	private String internalid;

	@JsonProperty("from_name")
	private String fromName;

	@JsonProperty("from_mail")
	private String fromMail;

	@JsonProperty("to_mail")
	private String toMail;

	private String subject;

	private Integer attachments;

	private String senderid;

	//private EmailContentEntityDto emailContent = new EmailContentEntityDto();
	
	public EmailEntityDto(EmailEntity email) {
		BeanUtils.copyProperties(email, this, "emailContent");
		//BeanUtils.copyProperties(email.getEmailContent(), this.getEmailContent());
		this.senderid = email.getSender().getCode();
	}
	
}
