package br.com.rti.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rti.domain.entities.SenderEntity;

public interface SenderEntityRepository extends JpaRepository<SenderEntity, Long> {

}
