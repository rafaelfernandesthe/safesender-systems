package br.com.rti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rti.domain.entities.ClientEntity;
import br.com.rti.domain.repositories.ClientEntityRepository;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ClientService {

	@Autowired
	private ClientEntityRepository clientEntityRepository;

	public List<ClientEntity> findAll() {
		log.info("findAll");
		return clientEntityRepository.findAll();
	}

}
