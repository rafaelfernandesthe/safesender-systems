package br.com.rti.apiwebhookreceiver.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.rti.apiwebhookreceiver.processors.EmailEventSaveBatchProcessor;
import br.com.rti.apiwebhookreceiver.processors.EmailEventSendToWebhookUrlProcessor;
import br.com.rti.domain.dtos.restapi.EmailEventPmtaDto;
import br.com.rti.routes.configs.ArrayListAggregationStrategy;
import br.com.rti.routes.processors.ErrorAsyncProcessor;

@Component
public class WebhookReceivedToSaveRabbitMqConsumerRoute extends RouteBuilder {

	private static final int QTD_MAX = 100;
	private static final Long WATTING_SECONDS = 1L;
	
	@Value("${rabbitmq.exchange}")
	public String exchange;
	
	@Value("${rabbitmq.webhook.ReceivedToSaveAndSend.key}")
	public String webhookReceivedToSaveAndSendkey;
	
	@Value("${rabbitmq.webhook.ReceivedToSaveAndSend.queue}")
	public String webhookReceivedToSaveAndSendQueue;

	@Autowired
	private EmailEventSaveBatchProcessor mailEventSaveBatchProcessor;
	
	@Autowired
	private EmailEventSendToWebhookUrlProcessor emailEventSendToWebhookUrlProcessor;

	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();

		from("spring-rabbitmq:" + exchange + 
				"?routingKey=" + webhookReceivedToSaveAndSendkey + 
				"&queues=" + webhookReceivedToSaveAndSendQueue).id(this.getClass().getSimpleName())
			.unmarshal().json(JsonLibrary.Gson, EmailEventPmtaDto.class)
			.aggregate(new ArrayListAggregationStrategy()).constant(true).completionTimeout(WATTING_SECONDS * 1000).completionSize(QTD_MAX)
			.threads(1, 1)
			.delay(1000)
			.process(mailEventSaveBatchProcessor)
			.process(emailEventSendToWebhookUrlProcessor)
			.end();
		
	}
}