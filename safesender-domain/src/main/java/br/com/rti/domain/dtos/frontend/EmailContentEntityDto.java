package br.com.rti.domain.dtos.frontend;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailContentEntityDto {

	private Long id;

	private String textContent;

	private String htmlContent;

}
