package br.com.rti.routes.disabled;
//package br.com.rti.routes;
//
//import org.apache.camel.builder.RouteBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import br.com.rti.routes.processors.MensagemSenderBatchSpringProcessor;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Component
//public class EmailToSaveRabbitMQRoute extends RouteBuilder {
//
//	private static final int QTD_MAX = 25;
//	private static final Long WATTING_SECONDS = 3L;
//
//	@Autowired
//	private MensagemSenderBatchSpringProcessor messageBatchProcessor;
//
//	@Override
//	public void configure() throws Exception {

//		onException(Exception.class).handled(true).process(new ProcessErrorAsync())
		// .wireTap("jms:queue:" + RabbitMQConfig.TO_SAVE_QUEUE_ERROR)
//		.end();

//		GsonDataFormat jsonDataFormat = new GsonDataFormat(MailSenderRequestDto.class);

//		from("direct:emailToSave").id(this.getClass().getSimpleName()).log("direct:emailToSave")
//		.marshal(jsonDataFormat).log("direct:emailToSave:marshal")
//				.to("spring-rabbitmq:" + RabbitMQConfig.PMTA_EXCHANGE +
//						"?routingKey=" + RabbitMQConfig.ROUTING_KEY +
//						"&queues=" + RabbitMQConfig.TO_SAVE_QUEUE).log("direct:emailToSave:rabbitmq")
//		.end();
//
//	}
//}