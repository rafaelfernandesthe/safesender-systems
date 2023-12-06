package br.com.rti.apismtp.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.rti.apismtp.processors.MailSaveBatchProcessor;
import br.com.rti.apismtp.processors.MensagemSenderBatchSpringProcessor;
import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import br.com.rti.routes.configs.ArrayListAggregationStrategy;
import br.com.rti.routes.processors.ErrorAsyncProcessor;

@Component
public class EmailToSaveAndSendRabbitMqConsumerRoute extends RouteBuilder {

	private static final int QTD_MAX = 100;
	private static final Long WATTING_SECONDS = 1L;
	
	@Value("${rabbitmq.exchange}")
	public String exchange;
	
	@Value("${rabbitmq.smtp.EmailToSaveAndSend.key}")
	public String smtpEmailToSaveAndSendKey;
	
	@Value("${rabbitmq.smtp.EmailToSaveAndSend.queue}")
	public String smtpEmailToSaveAndSendQueue;

	@Autowired
	private MailSaveBatchProcessor mailSaveBatchProcessor;
	
	@Autowired
	private MensagemSenderBatchSpringProcessor mensagemSenderBatchSpringProcessor;

	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();
		
		from("spring-rabbitmq:" + exchange + 
				"?routingKey=" + smtpEmailToSaveAndSendKey + 
				"&queues=" + smtpEmailToSaveAndSendQueue).id(this.getClass().getSimpleName())
			.unmarshal().json(JsonLibrary.Gson, MailSenderRequestDto.class)
			.aggregate(new ArrayListAggregationStrategy()).constant(true).completionTimeout(WATTING_SECONDS * 1000).completionSize(QTD_MAX)
			.threads(1, 1)
			.delay(1000)
			.process(mailSaveBatchProcessor)
			.process(mensagemSenderBatchSpringProcessor)
			.end();
		
	}
}