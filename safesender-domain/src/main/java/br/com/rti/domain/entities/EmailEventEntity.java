package br.com.rti.domain.entities;


import java.util.Date;

import br.com.rti.domain.enums.EmailEventStatus;
import br.com.rti.domain.enums.EmailEventType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class EmailEventEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private EmailEventType eventType;
	
	@Enumerated(EnumType.STRING)
	private EmailEventStatus status;

	@Temporal(TemporalType.TIMESTAMP)
	private Date eventDate;

	private String response;
	
	@Lob
	@Column(columnDefinition = "TEXT")
	private String responseDialg;

	@ManyToOne
	@JoinColumn(name = "fk_id_sender", nullable = false)
	private SenderEntity sender;
	
	private String internalid;
	
	private String uniqueid;
	
	private String fromMail;
	
	private String toMail;
	
	private int tryCount;
	
}
