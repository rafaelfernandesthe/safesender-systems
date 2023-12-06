package br.com.rti.apiwebhookreceiver.configs;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonHttpMessageConverterCustom extends GsonHttpMessageConverter {

	public GsonHttpMessageConverterCustom() {
		super(customGson());
	}

	private static Gson customGson() {
		return new GsonBuilder().create();
	}

	@Override
	protected void writeInternal(Object o, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		OutputStreamWriter writer = new OutputStreamWriter(outputMessage.getBody(), StandardCharsets.UTF_8);
		customGson().toJson(o, writer);
		writer.close();
	}
}