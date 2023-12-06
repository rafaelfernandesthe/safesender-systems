package br.com.rti.domain.dtos.frontend;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.rti.domain.configs.CustomDateSerializer;
import br.com.rti.domain.configs.CustomEmailEventStatusSerializer;
import br.com.rti.domain.configs.CustomEmailEventTypeSerializer;
import br.com.rti.domain.entities.EmailEventEntity;
import br.com.rti.domain.enums.EmailEventStatus;
import br.com.rti.domain.enums.EmailEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailEventEntityDto {
	
	@JsonProperty("event_type")
	@JsonSerialize(using = CustomEmailEventTypeSerializer.class)
	private EmailEventType eventType;

	@JsonSerialize(using = CustomEmailEventStatusSerializer.class)
	private EmailEventStatus status;

	@JsonProperty("event_date")
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date eventDate;

	private String response;

	private String responseDialg;

	private String internalid;

	private String uniqueid;

	private String senderid;

	private String fromMail;

	private String toMail;

	private int tryCount;

	public EmailEventEntityDto(EmailEventEntity event) {
		if (event != null) {
			BeanUtils.copyProperties(event, this);
			this.senderid = event.getSender().getCode();
		}
	}

}
