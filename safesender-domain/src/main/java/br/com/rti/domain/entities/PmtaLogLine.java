package br.com.rti.domain.entities;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
public class PmtaLogLine {

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long id;

	@Temporal( TemporalType.TIMESTAMP )
	private Date eventDate;

	@Column( columnDefinition = "TEXT" )
	private String originalLine;

	private boolean processed;

}
