package br.com.rti.apismtp.configs;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.google.gson.Gson;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public GsonHttpMessageConverter gsonHttpMessageConverter() {
		Gson gson = new Gson();
		return new GsonHttpMessageConverter( gson );
	}

	@Override
	public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
		converters.add( gsonHttpMessageConverter() );
	}
}