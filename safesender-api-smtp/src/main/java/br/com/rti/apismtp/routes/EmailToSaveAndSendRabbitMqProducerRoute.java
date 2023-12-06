package br.com.rti.apismtp.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.rti.routes.processors.ErrorAsyncProcessor;

@Component
public class EmailToSaveAndSendRabbitMqProducerRoute extends RouteBuilder {

	@Value("${rabbitmq.exchange}")
	public String exchange;
	
	@Value("${rabbitmq.smtp.EmailToSaveAndSend.key}")
	public String smtpEmailToSaveAndSendKey;
	
	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();

		from("direct:emailToSaveAndSend").id(this.getClass().getSimpleName())
			.marshal().json(JsonLibrary.Gson)
			.to("spring-rabbitmq:" + exchange + 
					"?routingKey=" + smtpEmailToSaveAndSendKey)
			.end();
	}
	
}