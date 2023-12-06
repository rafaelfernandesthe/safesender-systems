package br.com.rti.domain.configs;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.rti.domain.enums.EmailEventType;

public class EmailEventTypeAdapter implements JsonSerializer<EmailEventType> {

	@Override
	public JsonElement serialize(EmailEventType eventType, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(eventType.getDescription());
	}

}