package br.com.rti.domain.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rti.domain.entities.EmailEventEntity;

public interface EmailEventEntityRepository extends JpaRepository<EmailEventEntity, Long> {

	Optional<EmailEventEntity> findOneByInternalidOrderByEventDateDesc(String internalid);

	List<EmailEventEntity> findByInternalidOrderByEventDateDesc(String internalid);

}
