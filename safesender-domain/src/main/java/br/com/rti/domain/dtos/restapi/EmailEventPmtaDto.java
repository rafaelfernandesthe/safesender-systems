package br.com.rti.domain.dtos.restapi;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmailEventPmtaDto {
	
	private String type;
	private String timeLogged;
	private String timeQueued;
	private String orig;
	private String rcpt;
	private String orcpt;
	private String dsnAction;
	private String dsnStatus;
	private String dsnDiag;
	private String dsnMta;
	private String srcType;
	private String srcMta;
	private String dlvType;
	private String dlvSourceIp;
	private String dlvDestinationIp;
	private String dlvEsmtpAvailable;
	private String dlvSize;
	private String vmta;
	private String jobId;
	private String envId;
	private String queue;
	private String vmtaPool;

}
