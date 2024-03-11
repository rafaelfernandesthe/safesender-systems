package br.com.rti.domain.dtos.restapi;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailSenderAttachmentRequestDto {

	@SerializedName( value = "file_name" )
	public String fileName;

	@SerializedName( value = "file_contentBase64" )
	public String fileContentBase64;
}
