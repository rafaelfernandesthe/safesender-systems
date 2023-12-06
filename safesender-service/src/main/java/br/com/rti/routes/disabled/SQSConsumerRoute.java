package br.com.rti.routes.disabled;
//package br.com.rti.routes;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.stereotype.Component;
//
//import br.com.rti.routes.processor.ErrorAsyncProcessor;
//
//@Component
//public class SQSConsumerRoute extends RouteBuilder {
//
//	@Override
//	public void configure() throws Exception {
//
//		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();
//
//		from("aws2-sqs://ReceiveEmailRequestQueue.fifo").id(this.getClass().getSimpleName())
//			.threads(10, 10)
//			.to("direct:aggregator").end();
//
//	}
//}