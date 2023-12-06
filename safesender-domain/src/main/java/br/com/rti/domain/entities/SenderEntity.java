package br.com.rti.domain.entities;


import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SenderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date insertDate;

	private String code;

	private String token;
	
	private String webhookUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fk_id_client", nullable = false)
	private ClientEntity client;
	
	@OneToMany(mappedBy = "sender", fetch = FetchType.LAZY)
	private List<EmailEntity> emails;

}
