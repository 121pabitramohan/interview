package com.innoventes.test.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "company")
@Entity
public class Company extends BaseEntity {

	@Id
	@SequenceGenerator(sequenceName = "company_seq", allocationSize = 1, name = "company_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_seq")
	private Long id;

	@NotEmpty
	@Size(min = 5, message = "company name must be minimum of 5 character")
	@Column(name = "company_name", nullable = false)
	private String companyName;

	@Email
	@Column(name = "email", nullable = false)
	private String email;

	@PositiveOrZero(message = "must be positive or zero")
	@Column(name = "strength")
	private Integer strength;
	
	@Column(name = "website_url")
	private String webSiteURL;

	@Column(name = "company_code", unique = true)
	@Size(min = 5, message = "company code must be minimum of 5 character")
	@Pattern(regexp = "^[a-zA-Z]{2}[0-9]{2}[ENen]$", message = "Enter a valid format")
	private String companyCode;

}
