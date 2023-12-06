package br.com.rti.apiwebhookreceiver.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.rti.routes.processors.ErrorAsyncProcessor;

@Component
public class WebhookReceivedToSaveRabbitMqProducerRoute extends RouteBuilder {

	@Value("${rabbitmq.exchange}")
	public String exchange;
	
	@Value("${rabbitmq.webhook.ReceivedToSaveAndSend.key}")
	public String webhookReceivedToSaveAndSendkey;
	
	
	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();

		from("direct:webhookReceivedToSave").id(this.getClass().getSimpleName())
			.to("spring-rabbitmq:"
				+ exchange + "?routingKey=" 
				+ webhookReceivedToSaveAndSendkey)
			.end();
	}
}