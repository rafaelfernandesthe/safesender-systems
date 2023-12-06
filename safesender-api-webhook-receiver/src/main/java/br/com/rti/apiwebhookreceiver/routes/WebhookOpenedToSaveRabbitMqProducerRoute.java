package br.com.rti.apiwebhookreceiver.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.rti.routes.processors.ErrorAsyncProcessor;

@Component
public class WebhookOpenedToSaveRabbitMqProducerRoute extends RouteBuilder {

	@Value("${rabbitmq.exchange}")
	public String exchange;
	
	@Value("${rabbitmq.webhook.OpenedToSaveAndSend.key}")
	public String webhookOpenedToSaveAndSendkey;
	
	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();

		from("direct:webhookOpenedToSave").id(this.getClass().getSimpleName())
			.marshal().json(JsonLibrary.Gson)
			.to("spring-rabbitmq:"
				+ exchange + "?routingKey=" 
				+ webhookOpenedToSaveAndSendkey)
			.end();
	}
}