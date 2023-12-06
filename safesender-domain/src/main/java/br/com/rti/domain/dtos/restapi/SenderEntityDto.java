package br.com.rti.domain.dtos.restapi;

import java.util.Date;

import lombok.Data;

@Data
public class SenderEntityDto {

	private Long id;

	private Date insertDate;

	private String code;

	private String token;

	private String webhookUrl;

	private Long clientId;

}