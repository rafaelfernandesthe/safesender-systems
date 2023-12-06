package br.com.rti.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rti.domain.entities.ClientEntity;

public interface ClientEntityRepository extends JpaRepository<ClientEntity, Long> {

}
