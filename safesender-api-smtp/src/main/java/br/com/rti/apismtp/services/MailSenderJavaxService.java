package br.com.rti.apismtp.services;
//package br.com.rti.service;
//
//import java.time.Duration;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.Base64;
//import java.util.List;
//import java.util.Properties;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.springframework.stereotype.Service;
//import org.springframework.util.ObjectUtils;
//import org.springframework.web.util.HtmlUtils;
//
//import br.com.rti.domain.dtos.MailSenderRequestDto;
//import jakarta.activation.DataSource;
//import jakarta.mail.BodyPart;
//import jakarta.mail.MessagingException;
//import jakarta.mail.Multipart;
//import jakarta.mail.internet.MimeBodyPart;
//import jakarta.mail.internet.MimeMessage;
//import jakarta.mail.internet.MimeMultipart;
//import jakarta.mail.util.ByteArrayDataSource;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//@Service
//public class MailSenderJavaxService {
//
//	private static final String CERTIFICATE = "certificate";
//	private static final String CERTIFICATE_CONTENT_TYPE = "application/x-pkcs12";
//	private static final String FILE_TYPE = "application/octet-stream";
//
//	public void sendMail(MailSenderRequestDto emailRequest) throws Exception {
//		sendMail(Arrays.asList(emailRequest));
//	}
//
//	public void sendMail(List<MailSenderRequestDto> emailRequestList) throws Exception {
//		
//		LocalDateTime start = LocalDateTime.now();
//		log.info("INICIO - {} ", start);
//
////		List<BulkEmailSender> messageList = new ArrayList<>();
//		
//		ExecutorService executorService = Executors.newFixedThreadPool(10);
//		
//		try {
//		
//		
//		for (MailSenderRequestDto emailRequest : emailRequestList) {
//			
//			executorService.submit(() -> {
//				
//				try {
//					
//				BulkEmailSender bulkEmailSender = new BulkEmailSender(emailRequest, prepareProp());
//	
//				// preparando conteudo
//				String content = htmlUnescape(emailRequest.getHtml());
//				Document doc = Jsoup.parse(content);
//				boolean isHtml = doc.select("html").size() > 0;
//				boolean certSignedMail = !ObjectUtils.isEmpty(emailRequest.getCertificate());
//				boolean isMimeMessage = isHtml || certSignedMail || !ObjectUtils.isEmpty(emailRequest.getAttachments());
//	
//				MimeMessage mimeMessage = bulkEmailSender.getMimeMessage();
//				Multipart multipart = new MimeMultipart();
//				
//				BodyPart bodyPart = new MimeBodyPart();
//				try {
//					bodyPart.setContent(content, "text/html;charset=utf-8");
//					multipart.addBodyPart(bodyPart);
//					mimeMessage.setContent(multipart);
//				} catch (MessagingException e) {
//					e.printStackTrace();
//				}
//				
//					mimeMessage.addHeader("x-envid", emailRequest.getInternalid());
//				
//				
//	//			if (certSignedMail) {
//	//				File certificado = new File("caminho/para/certificado.p12");
//	//				InputStreamSource inputStreamSource = new ByteArrayResource(Files.readAllBytes(certificado.toPath()));
//	//				helper.addInline(CERTIFICATE, inputStreamSource, CERTIFICATE_CONTENT_TYPE);
//	//				helper.getMimeMessage().setContentID(CERTIFICATE);
//	//			}
//	//
//	//			if (!ObjectUtils.isEmpty(emailRequest.getAttachments())) {
//	//				for (MailSenderAttachmentRequestDto file : emailRequest.getAttachments()) {
//	//					try {
//	//						helper.addAttachment(file.getFileName(), base64ContentToFileMem(file.getFileContentBase64()));
//	//					} catch (MessagingException e) {
//	//						e.printStackTrace();
//	//					}
//	//				}
//	//			}
//	
//	//			messageList.add(bulkEmailSender);
//				bulkEmailSender.sendEmail(mimeMessage);
//				
//				} catch (MessagingException e) {
//					log.error("Erro ENVIO loop emailRequestList", e);
//				}
//			});
//
//		}
//
//		} catch (Exception e) {
//			log.error("Exception 1", e);
//		} finally {
//        	executorService.shutdown();
//
//            // Espera até que todas as tarefas tenham concluído
//            try {
//                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
//                    executorService.shutdownNow();
//                    if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
//                        log.error("Pool de threads não terminou");
//                }
//            } catch (InterruptedException e) {
//                executorService.shutdownNow();
//                Thread.currentThread().interrupt();
//            }
//            
//            LocalDateTime end = LocalDateTime.now();
//            log.info("FIM - {} - totalTime(seconds): {}", end, Duration.between(start, end).toSeconds());
//        }
//	}
//
//	private Properties prepareProp() {
//		Properties emailConfig = new Properties();
//		emailConfig.put("mail.smtp.host", "139.99.69.201");
//		emailConfig.put("mail.smtp.port", "2525");
//		emailConfig.put("mail.smtp.user", "test");
//		emailConfig.put("mail.smtp.auth", true);
//		emailConfig.put("mail.smtp.auth.mechanisms", "CRAM-MD5");
//		emailConfig.put("mail.smtp.sasl.enable", true);
//		emailConfig.put("mail.smtp.connectionpoolsize", 10);
//		return emailConfig;
//	}
//
//	private DataSource base64ContentToFileMem(String fileContentBase64) {
//		byte[] fileBytes = Base64.getDecoder().decode(fileContentBase64);
//		return new ByteArrayDataSource(fileBytes, FILE_TYPE);
//	}
//
//	private String htmlUnescape(String html) {
//		return HtmlUtils.htmlUnescape(html);
//	}
//
//	private String htmlToText(Document doc) {
//		try {
//			return doc.body().text();
//		} catch (Exception e) {
//			log.error("htmlToText FAIL", e);
//		}
//		return null;
//	}
//
//}
