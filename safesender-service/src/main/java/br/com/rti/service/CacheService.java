package br.com.rti.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.rti.domain.entities.ClientEntity;
import br.com.rti.domain.entities.SenderEntity;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CacheService {

	private List<ClientEntity> clientList = null;
	private List<SenderEntity> senderList = null;
	private List<String> senderCodeList = null;
	private List<String> senderTokenList = null;

	@Autowired
	private ClientService clientService;

	public List<ClientEntity> getClientList() {
		return this.getClientList(false);
	}

	public List<ClientEntity> getClientList(boolean forceLoad) {
		if (clientList == null || forceLoad) {
			log.info("renew cache getClientList()");
			clientList = clientService.findAll();
			senderList = clientList.stream().flatMap(c -> c.getSender().stream()).collect(Collectors.toList());
			senderCodeList = senderList.stream().map(c -> c.getCode()).collect(Collectors.toList());
			senderTokenList = senderList.stream().map(s -> s.getToken()).collect(Collectors.toList());
		}
		return clientList;
	}

	public List<SenderEntity> getSenderList() {
		if (senderList == null) {
			log.info("renew cache getSenderList()");
			senderList = getClientList().stream().flatMap(c -> c.getSender().stream()).collect(Collectors.toList());
		}
		return senderList;
	}

	public List<String> getSenderCodeList() {
		if (senderCodeList == null) {
			log.info("renew cache getSenderList()");
			senderCodeList = getSenderList().stream().map(c -> c.getCode()).collect(Collectors.toList());
		}
		return senderCodeList;
	}

	public List<String> getSenderTokenList() {
		if (senderTokenList == null) {
			log.info("renew cache getTokenList()");
			senderTokenList = getSenderList().stream().map(s -> s.getToken()).collect(Collectors.toList());
		}
		return senderTokenList;
	}
	
	public SenderEntity getSenderBySendercode(String senderCode) {
		return getSenderList().stream().filter(s -> s.getCode().equals(senderCode)).findFirst().get();
	}

	@Scheduled(fixedDelay = 1 * 60 * 1000)
	public void reset() {
		this.clientList = this.getClientList(true);
	}

}
