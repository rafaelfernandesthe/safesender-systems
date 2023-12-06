package br.com.rti.routes.disabled;
//package br.com.rti.routes;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.dataformat.JsonLibrary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import br.com.rti.rest.dto.MailSenderRequestDto;
//import br.com.rti.routes.config.ArrayListAggregationStrategy;
//import br.com.rti.routes.processor.MensagemBatchSenderSpringProcessor;
//import br.com.rti.routes.processor.ErrorAsyncProcessor;
//
//@Component
//public class SQSConsumerAggregatorRoute extends RouteBuilder {
//	
//	private static final int QTD_MAX = 100;
//	private static final Long WATTING_SECONDS = 2L;
//	
//	@Autowired
//	private MensagemBatchSenderSpringProcessor messageBatchProcessor;
//
//    @Override
//    public void configure() throws Exception {
//    	
//		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();
//		
//		from("direct:aggregator").id(this.getClass().getSimpleName())
//		.unmarshal().json(JsonLibrary.Gson, MailSenderRequestDto.class)
//		.aggregate(new ArrayListAggregationStrategy()).constant(true).completionTimeout(WATTING_SECONDS * 1000).completionSize(QTD_MAX)
//		.process(messageBatchProcessor)
//		.end();
//    	
//    }
//}