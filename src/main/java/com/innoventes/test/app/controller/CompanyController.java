package com.innoventes.test.app.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.innoventes.test.app.dto.CompanyDTO;
import com.innoventes.test.app.entity.Company;
import com.innoventes.test.app.exception.ValidationException;
import com.innoventes.test.app.mapper.CompanyMapper;
import com.innoventes.test.app.service.CompanyService;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private CompanyService companyService;

	@Autowired
	private MessageSource messageSource;

	@GetMapping("/companies")
	public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
		List<Company> companyList = companyService.getAllCompanies();
		
		List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();
		
		for (Company entity : companyList) {
			companyDTOList.add(companyMapper.getCompanyDTO(entity));
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.status(HttpStatus.OK).location(location).body(companyDTOList);
	}

	//localhost:8081/api/v1/companies

	@PostMapping("/companies")
	public ResponseEntity<CompanyDTO> addCompany(@Valid @RequestBody CompanyDTO companyDTO)
			throws ValidationException {
		Company company = companyMapper.getCompany(companyDTO);
		Company newCompany = companyService.addCompany(company);
		CompanyDTO newCompanyDTO = companyMapper.getCompanyDTO(newCompany);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCompany.getId())
				.toUri();
		return ResponseEntity.created(location).body(newCompanyDTO);
	}
//http://localhost:8081/companies/4
	@PutMapping(value = "/companies/{id}")
	public ResponseEntity<CompanyDTO> updateCompany(@PathVariable(value = "id") Long id,
			@Valid @RequestBody CompanyDTO companyDTO) throws ValidationException {
		Company company = companyMapper.getCompany(companyDTO);
		Company updatedCompany = companyService.updateCompany(id, company);
		CompanyDTO updatedCompanyDTO = companyMapper.getCompanyDTO(updatedCompany);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
		return ResponseEntity.status(HttpStatus.OK).location(location).body(updatedCompanyDTO);
	}
//http://localhost:8081/a1/v1/companies/4
	@DeleteMapping("/companies/{id}")
	public ResponseEntity<CompanyDTO> deleteCompany(@PathVariable("id") Long id) {
		companyService.deleteCompany(id);
		return ResponseEntity.noContent().build();
	}

	public String getMessage(String exceptionCode) {
		return messageSource.getMessage(exceptionCode, null, LocaleContextHolder.getLocale());
	}
     //http://localhost:8081/api/v1/get/1
	@GetMapping("/get/{id}")
	public ResponseEntity<CompanyDTO> getById(@PathVariable("id") long id){
		CompanyDTO companyDTO=companyService.getCompanyById(id);
		return new ResponseEntity<>(companyDTO, HttpStatus.FOUND);
	}

	@GetMapping("/get/code/{companyCode}")
	public ResponseEntity<Company> getByCompanyId(@PathVariable("companyCode") String companyCode){
		Company company=companyService.getCompanyByCompanyId(companyCode);
		return new ResponseEntity<>(company, HttpStatus.FOUND);
	}

	@DeleteMapping("/delete/company/{id}")
	public ResponseEntity<String> deleteACompany(@PathVariable("id") Long id){
		companyService.deleteACompny(id);
		return new ResponseEntity<>("Company successfully deleted for id number"+ id, HttpStatus.OK);
	}

}
