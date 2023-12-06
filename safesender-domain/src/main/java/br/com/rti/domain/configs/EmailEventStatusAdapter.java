package br.com.rti.domain.configs;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.com.rti.domain.enums.EmailEventStatus;

public class EmailEventStatusAdapter implements JsonSerializer<EmailEventStatus> {

	@Override
	public JsonElement serialize(EmailEventStatus eventType, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(eventType.getDescription());
	}

}