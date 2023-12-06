package br.com.rti.apismtp.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.rti.apismtp.processors.MailSaveBatchProcessor;
import br.com.rti.apismtp.processors.MensagemSenderBatchSpringProcessor;
import br.com.rti.routes.configs.ArrayListAggregationStrategy;
import br.com.rti.routes.processors.ErrorAsyncProcessor;

@Component
public class EmailToSaveAndSendDirectRoute extends RouteBuilder {

	private static final int QTD_MAX = 100;
	private static final Long WATTING_SECONDS = 1L;

	@Autowired
	private MailSaveBatchProcessor mailSaveBatchProcessor;
	
	@Autowired
	private MensagemSenderBatchSpringProcessor mensagemSenderBatchSpringProcessor;

	@Override
	public void configure() throws Exception {

		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();

//		from("direct:emailToSaveAndSend").id(this.getClass().getSimpleName())
//			.aggregate(new ArrayListAggregationStrategy()).constant(true).completionTimeout(WATTING_SECONDS * 1000).completionSize(QTD_MAX)
//			.threads(1, 1)
//			.delay(1000)
//			.process(mailSaveBatchProcessor)
//			.process(mensagemSenderBatchSpringProcessor)
//			.end();
		
	}
}