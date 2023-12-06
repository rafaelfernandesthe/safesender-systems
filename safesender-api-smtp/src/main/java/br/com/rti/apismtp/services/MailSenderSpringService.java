package br.com.rti.apismtp.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.HtmlUtils;

import br.com.rti.domain.dtos.restapi.EmailEventPmtaDto;
import br.com.rti.domain.dtos.restapi.MailSenderAttachmentRequestDto;
import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
import jakarta.activation.DataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class MailSenderSpringService {
	
	private static final String DISCARD = "discard";

	@Autowired
	private JavaMailSender mailSender;
	
	@Produce("direct:webhookDiscarded")
	private ProducerTemplate producerTemplate;

	private String URL_OPEN_TRACKING = "https://link.safesender.tech/opened/";
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
	private static final String CERTIFICATE = "certificate";
	private static final String CERTIFICATE_CONTENT_TYPE = "application/x-pkcs12";
	private static final String FILE_TYPE = "application/octet-stream";
	private static final String REGEX_EMAIL = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

	public void sendMail(MailSenderRequestDto emailRequest) throws Exception {
		sendMail(Arrays.asList(emailRequest));
	}

	public void sendMail(List<MailSenderRequestDto> emailRequestList) throws Exception {

		LocalDateTime start = LocalDateTime.now();
		log.info("INICIO sendMail LOOP - {} ", start);

		List<MailSenderRequestDto> rejectList = new ArrayList<>();
		
		ExecutorService executorService = Executors.newFixedThreadPool(100);

		try {

			for (MailSenderRequestDto emailRequest : emailRequestList) {

				executorService.submit(() -> {

					try {
						
						if(!isValidMail(emailRequest.getToMail())){
							rejectList.add(emailRequest);
							return;
						}

						// preparando conteudo
						String content = htmlUnescape(emailRequest.getHtml());
						Document doc = Jsoup.parse(content);
						boolean isHtml = doc.select("html").size() > 0;
						boolean certSignedMail = !ObjectUtils.isEmpty(emailRequest.getCertificate());
						boolean isMimeMessage = isHtml || certSignedMail
								|| !ObjectUtils.isEmpty(emailRequest.getAttachments());

						String internalid = emailRequest.getInternalid();
						
						if (isHtml) {
							Element body = doc.body();
							
							//TOP
							Element divTop = doc.createElement("div");
							divTop.attr("style",
									"color:transparent;visibility:hidden;opacity:0;font-size:0px;border:0;max-height:1px;width:1px;margin:0px;padding:0px;border-width:0px!important;display:none!important;line-height:0px!important;");

							Element imgTop = doc.createElement("img");
							imgTop.attr("alt", "top click track");
							imgTop.attr("border", "0");
							imgTop.attr("width", "1");
							imgTop.attr("height", "1");
							imgTop.attr("src", URL_OPEN_TRACKING + internalid);

							divTop.appendChild(imgTop);
							body.children().first().before(divTop);
							
							//BOTTOM
							Element imgBottom = doc.createElement("img");
							imgBottom.attr("alt", "bottom click track");
							imgBottom.attr("border", "0");
							imgBottom.attr("width", "1");
							imgBottom.attr("height", "1");
							imgBottom.attr("src", URL_OPEN_TRACKING + internalid );

							body.appendChild(imgBottom);
						}

						jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();

						MimeMessageHelper helper = isMimeMessage ? new MimeMessageHelper(message, true)
								: new MimeMessageHelper(message);

						message.addHeader("x-envid", internalid);

						helper.setFrom(emailRequest.getFromMail());
						if (!ObjectUtils.isEmpty(emailRequest.getFromName())) {
							helper.setFrom(emailRequest.getFromMail(), emailRequest.getFromName());
						}

						helper.setTo(emailRequest.getToMail());
						helper.setSubject(emailRequest.getSubject());

						if (!ObjectUtils.isEmpty(emailRequest.getHtml())) {
							helper.setText(htmlToText(doc), doc.html());
						} else {
							helper.setText(emailRequest.getText());
						}

						if (certSignedMail) {
							File certificado = new File("caminho/para/certificado.p12");
							InputStreamSource inputStreamSource = new ByteArrayResource(
									Files.readAllBytes(certificado.toPath()));
							helper.addInline(CERTIFICATE, inputStreamSource, CERTIFICATE_CONTENT_TYPE);
							helper.getMimeMessage().setContentID(CERTIFICATE);
						}

						if (!ObjectUtils.isEmpty(emailRequest.getAttachments())) {
							for (MailSenderAttachmentRequestDto file : emailRequest.getAttachments()) {
								try {
									helper.addAttachment(file.getFileName(),
											base64ContentToFileMem(file.getFileContentBase64()));
								} catch (MessagingException e) {
									e.printStackTrace();
								}
							}
						}

						if(emailRequest.isToValidation()) {
							//nothing
							log.info("OK ToValidation internalid:{}", internalid);
						} else if(emailRequest.isToSaveFile()) {
							//salvar email para testes de performance com rede
							try (OutputStream outputStream = new FileOutputStream("/opt/app-safesender/test-files/" + internalid + ".eml")) {
								message.writeTo(outputStream);
					        } catch (IOException | MessagingException e) {
					            e.printStackTrace();
				        }} else {
				        	mailSender.send(message);
				        }

					} catch (Exception e) {
						log.error("Erro ENVIO loop emailRequestList", e);
					}

				});
			}
			
		} catch (Exception e) {
			log.error("Exception 1", e);
		} finally {
			executorService.shutdown();

			// Espera até que todas as tarefas tenham concluído
			try {
				if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
					executorService.shutdownNow();
					if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
						log.error("Pool de threads não terminou");
				}
			} catch (InterruptedException e) {
				executorService.shutdownNow();
				Thread.currentThread().interrupt();
			}
			
			LocalDateTime end = LocalDateTime.now();
			log.info("FIM sendMail LOOP - {} - totalTime(milli): {} - totalTime(seconds): {}", end, Duration.between(start, end).toMillis(), Duration.between(start, end).toSeconds());
		}
		
		sendToDiscard(rejectList);
	}

	private void sendToDiscard(List<MailSenderRequestDto> rejectList) {
		log.info("INICIO sendToDiscard: {}", rejectList.size());
		rejectList.stream()
		.map(emailRequest -> EmailEventPmtaDto.builder()
			.timeLogged(SDF.format(new Date()))
			.orig(emailRequest.getFromMail())
			.rcpt(emailRequest.getToMail())
			.dlvType(DISCARD)
			.envId(emailRequest.getInternalid())
			.dsnStatus("5.1.3 (discard)")
			.dsnDiag("Bad recipient address syntax")
			.build()				
		).forEach(eventDto -> {
			log.info("EMAIL DISCARDED internalid:{}", eventDto.getEnvId());
			producerTemplate.asyncSendBody(producerTemplate.getDefaultEndpoint(), eventDto);
		});
		log.info("FIM sendToDiscard: {}", rejectList.size());
	}

	private boolean isValidMail(String toMail) {
		return Pattern.compile(REGEX_EMAIL)
		      .matcher(toMail)
		      .matches();
	}

	private DataSource base64ContentToFileMem(String fileContentBase64) {
		byte[] fileBytes = Base64.getDecoder().decode(fileContentBase64);
		return new ByteArrayDataSource(fileBytes, FILE_TYPE);
	}

	private String htmlUnescape(String html) {
		return HtmlUtils.htmlUnescape(html);
	}

	private String htmlToText(Document doc) {
		try {
			return doc.body().text();
		} catch (Exception e) {
			log.error("htmlToText FAIL", e);
		}
		return null;
	}

}
