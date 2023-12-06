package br.com.rti.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rti.domain.entities.EmailContentEntity;

public interface EmailContentEntityRepository extends JpaRepository<EmailContentEntity, Long> {

}
