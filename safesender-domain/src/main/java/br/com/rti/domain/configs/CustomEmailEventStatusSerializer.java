package br.com.rti.domain.configs;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.rti.domain.enums.EmailEventStatus;

public class CustomEmailEventStatusSerializer extends JsonSerializer<EmailEventStatus> {

	@Override
	public void serialize(EmailEventStatus emailEventStatus, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(emailEventStatus.getDescription());
	}
}