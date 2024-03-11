package br.com.rti.domain.dtos.restapi;

import com.google.gson.annotations.SerializedName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MailSenderRequestDto {
	
	private boolean toValidation;
	
	private boolean toSaveFile;

	@Size(max = 30)
	private String uniqueid;

	private String internalid;

	private String token;

	private SenderEntityDto senderObj;

	private String sender;

	@SerializedName(value = "from_name")
	private String fromName;

	@NotBlank
	@NotNull
	@SerializedName(value = "from_mail")
	private String fromMail;

	@NotBlank
	@NotNull
	@SerializedName(value = "to_mail")
	private String toMail;

	@NotBlank
	@NotNull
	private String subject;

	private String certificate;

	private String text;

	private String html;

	private List<MailSenderAttachmentRequestDto> attachments;

}
