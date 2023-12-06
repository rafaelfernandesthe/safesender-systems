package br.com.rti.routes.disabled;
//package br.com.rti.routes;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.apache.camel.model.dataformat.JsonLibrary;
//import org.springframework.stereotype.Component;
//
//import br.com.rti.rest.dto.MailSenderRequestDto;
//import br.com.rti.routes.processor.ErrorAsyncProcessor;
//
//@Component
//public class SQSProducerRoute extends RouteBuilder {
//
//	@Override
//	public void configure() throws Exception {
//		
//		onException(Exception.class).handled(true).process(new ErrorAsyncProcessor()).end();
//
//		from("direct:emailToSaveAndSend").id(this.getClass().getSimpleName())
//			.setBody().method(this, "prepareToMarshal")
//			.marshal().json(JsonLibrary.Gson)
//			.to("aws2-sqs://ReceiveEmailRequestQueue.fifo");
//
//	}
//
//	public MailSenderRequestDto prepareToMarshal(MailSenderRequestDto obj) {
//		obj.getSenderObj().setInsertDate(null);
//		obj.getSenderObj().setEmails(null);
//		obj.getSenderObj().getClient().setInsertDate(null);
//		obj.getSenderObj().getClient().setSender(null);
//		return obj;
//	}
//
//}