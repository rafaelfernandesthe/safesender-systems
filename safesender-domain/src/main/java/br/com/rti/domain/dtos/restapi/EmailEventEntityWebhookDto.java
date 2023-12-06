package br.com.rti.domain.dtos.restapi;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import br.com.rti.domain.configs.CustomDateAdapter;
import br.com.rti.domain.configs.EmailEventStatusAdapter;
import br.com.rti.domain.configs.EmailEventTypeAdapter;
import br.com.rti.domain.enums.EmailEventStatus;
import br.com.rti.domain.enums.EmailEventType;
import lombok.Data;

@Data
@JsonInclude( JsonInclude.Include.NON_EMPTY )
public class EmailEventEntityWebhookDto {

	@SerializedName("event_type")
	@JsonAdapter(value = EmailEventTypeAdapter.class)
	private EmailEventType eventType;

	@JsonAdapter(value = EmailEventStatusAdapter.class)
	private EmailEventStatus status;

	@SerializedName("event_date")
	@JsonAdapter(value = CustomDateAdapter.class)
	private Date eventDate;

	private String response;

	@SerializedName("response_dialg")
	private String responseDialg;

	private String sender;

	@SerializedName("sender_webhook_url")
	private String senderWebhookUrl;

	private String internalid;

	private String uniqueid;

	@SerializedName("from_mail")
	private String fromMail;

	@SerializedName("to_mail")
	private String toMail;

	@SerializedName("try_count")
	private int tryCount;

}
