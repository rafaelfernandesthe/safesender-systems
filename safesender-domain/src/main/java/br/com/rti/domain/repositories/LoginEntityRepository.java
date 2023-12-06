package br.com.rti.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rti.domain.entities.LoginEntity;

public interface LoginEntityRepository extends JpaRepository<LoginEntity, Long> {

}
