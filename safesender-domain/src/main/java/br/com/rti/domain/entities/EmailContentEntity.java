package br.com.rti.domain.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class EmailContentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String textContent;

	@Lob
	@Column(columnDefinition = "TEXT")
	private String htmlContent;

	@OneToOne
	@JoinColumn(name = "fk_id_email", nullable = false)
	private EmailEntity email;

}
