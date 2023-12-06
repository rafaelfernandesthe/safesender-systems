package br.com.rti.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rti.domain.entities.EmailAttachmentEntity;

public interface EmailAttachmentEntityRepository extends JpaRepository<EmailAttachmentEntity, Long> {

}
