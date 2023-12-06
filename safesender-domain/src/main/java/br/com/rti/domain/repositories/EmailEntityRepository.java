package br.com.rti.domain.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.rti.domain.entities.EmailEntity;
import br.com.rti.domain.entities.SenderEntity;

public interface EmailEntityRepository extends JpaRepository<EmailEntity, Long> {

	Page<EmailEntity> findBySender(@Param("sender") SenderEntity sender, Pageable pageable);

	Optional<EmailEntity> findOneBySenderAndId(@Param("sender") SenderEntity sender, @Param("id") Long id);

}
