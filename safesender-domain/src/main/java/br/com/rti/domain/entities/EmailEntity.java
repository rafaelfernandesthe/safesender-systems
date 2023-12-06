package br.com.rti.domain.entities;


import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class EmailEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;

	private Long clientid;

	private String uniqueid;

	private String internalid;

	private String fromName;

	private String fromMail;

	private String toMail;

	private String subject;

	private Integer attachments;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_id_sender", nullable = false)
	private SenderEntity sender;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
	private EmailContentEntity emailContent;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "email", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EmailAttachmentEntity> emailAttachments;

}
