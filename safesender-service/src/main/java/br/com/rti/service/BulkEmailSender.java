//package br.com.rti.service;
//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.util.Date;
//import java.util.List;
//import java.util.Objects;
//import java.util.Properties;
//import java.util.function.Function;
//
//import org.springframework.boot.rsocket.server.RSocketServer.Transport;
//import org.springframework.util.ObjectUtils;
//
//import br.com.rti.domain.dtos.restapi.MailSenderRequestDto;
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.InternetAddress;
//import jakarta.mail.internet.MimeMessage;
//import lombok.extern.log4j.Log4j2;
//
//@Log4j2
//public class BulkEmailSender {
//
//	private MailSenderRequestDto emailInfo;
//	private Properties emailConfig;
//	private MimeMessage mimeMessage;
//
//	public BulkEmailSender(MailSenderRequestDto emailInfo, Properties emailConfig) {
//		this.emailInfo = emailInfo;
//		this.emailConfig = emailConfig;
//	}
//
//	public MailSenderRequestDto getEmailInfo() {
//		return emailInfo;
//	}
//
//	public void sendEmail(List<MimeMessage> mimeMessageList) {
//		for(MimeMessage m : mimeMessageList) {
//			this.sendEmail(m);
//		}
//	}
//	
//	public void sendEmail(MimeMessage mimeMessage) {
//		try {
//			Transport.send(mimeMessage);
//		} catch (Exception e) {
//			log.error("ERRO sendMail", e);
//		}
//	}
//
//	public MimeMessage getMimeMessage() {
//		mimeMessage = buildMimeMessage();
//		return mimeMessage;
//	}
//
//	private MimeMessage buildMimeMessage() {
//		Session session = Session.getInstance(emailConfig, new Authenticator() {
//			@Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication("test", "changeme");
//			}
//		});
//		MimeMessage mimeMessage = new MimeMessage(session);
//
//		try {
//			setFromAddress().andThen(setToAddress())/*.andThen(setCcAddress())*/.andThen(setSubject()).apply(mimeMessage);
//
//			mimeMessage.setSentDate(new Date());
//		} catch (Exception e) {
//			mimeMessage = null;
//			throw new RuntimeException("Error in setting from, to, cc address and subject of the email");
//		}
//
//		return mimeMessage;
//	}
//
//	private Function<MimeMessage, MimeMessage> setSubject() {
//		return mimeMessage -> {
//			if (!Objects.isNull(emailInfo.getSubject()) && !emailInfo.getSubject().isEmpty()) {
//				try {
//					mimeMessage.setSubject(emailInfo.getSubject());
//				} catch (Exception e) {
//					throw new RuntimeException("Could not set subject " + emailInfo.getSubject());
//				}
//			}
//			return mimeMessage;
//		};
//	}
//
//	private Function<MimeMessage, MimeMessage> setFromAddress() {
//		return mimeMessage -> {
//			if (!Objects.isNull(emailInfo.getFromMail()) && !emailInfo.getFromMail().isEmpty()) {
//				try {
//					InternetAddress fromAddress = new InternetAddress(emailInfo.getFromMail().trim());
//					if(!ObjectUtils.isEmpty(emailInfo.getFromName())){
//						fromAddress.setPersonal(emailInfo.getFromName());
//					}
//					mimeMessage.setFrom(fromAddress);
//				} catch (Exception e) {
//					throw new RuntimeException("Could not resolve from address " + emailInfo.getFromMail());
//				}
//			}
//			return mimeMessage;
//		};
//	}
//
//	private Function<MimeMessage, MimeMessage> setToAddress() {
//		return mimeMessage -> {
//			if (!Objects.isNull(emailInfo.getToMail()) && !emailInfo.getToMail().isEmpty()) {
//				try {
//				mimeMessage.setRecipients(Message.RecipientType.TO, emailInfo.getToMail());
////					mimeMessage.setRecipients(Message.RecipientType.TO, emailInfo.getToList().stream().map(to -> {
////						try {
////							return new InternetAddress(to);
////						} catch (AddressException e) {
////							throw new RuntimeException("Could not resolve to address");
////						}
////					}).toArray(InternetAddress[]::new));
//				} catch (MessagingException e) {
//					throw new RuntimeException(
//							"Could not set to address in message " + emailInfo.getToMail());
//				}
//			}
//			return mimeMessage;
//		};
//	}
//
////	private Function<MimeMessage, MimeMessage> setCcAddress() {
////		return mimeMessage -> {
////			if (!Objects.isNull(emailInfo.getToList()) && !emailInfo.getCcList().isEmpty()) {
////				try {
////					mimeMessage.setRecipients(Message.RecipientType.CC, emailInfo.getCcList().stream().map(to -> {
////						try {
////							return new InternetAddress(to);
////						} catch (AddressException e) {
////							throw new RuntimeException("Could not resolve cc address");
////						}
////					}).toArray(InternetAddress[]::new));
////				} catch (MessagingException e) {
////					throw new RuntimeException(
////							"Could not set cc address in message " + emailInfo.getCcList().toString());
////				}
////			}
////			return mimeMessage;
////		};
////	}
//
//}