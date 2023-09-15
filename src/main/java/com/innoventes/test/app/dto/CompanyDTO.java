package com.innoventes.test.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyDTO {

	private Long id;

	@NotEmpty
	@Size(min = 5, message = "company name must be minimum of 5 character")
	@NotNull
	private String companyName;

	@Email
	@NotNull
	private String email;

	@PositiveOrZero(message = "must be positive or zero")
	private Integer strength;
	
	private String webSiteURL;

	private String companyCode;
}
