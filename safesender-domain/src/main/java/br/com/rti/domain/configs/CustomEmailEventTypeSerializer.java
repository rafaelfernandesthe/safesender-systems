package br.com.rti.domain.configs;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import br.com.rti.domain.enums.EmailEventType;

public class CustomEmailEventTypeSerializer extends JsonSerializer<EmailEventType> {

	@Override
	public void serialize(EmailEventType emailEventType, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) throws IOException {
		jsonGenerator.writeString(emailEventType.getDescription());
	}
}