package com.MicroServices.JobApp.Services.Impl.Company;

import com.MicroServices.JobApp.Dto.Company.CompanyDTO;
import com.MicroServices.JobApp.Entity.Company;
import com.MicroServices.JobApp.Exceptions.ResourceNotFoundException;
import com.MicroServices.JobApp.Repository.CompanyRepository;
import com.MicroServices.JobApp.Services.Impl.Job.JobServicesImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;


@Service
public class CompanyServicesImpl implements CompanyServices {

    private static final Logger log = LoggerFactory.getLogger(JobServicesImpl.class);

    private final CompanyRepository companyRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public CompanyServicesImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public CompanyDTO saveCompany(CompanyDTO companyDTO) {
        Company company = modelMapper.map(companyDTO, Company.class);
        companyRepository.save(company);
        return modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO getCompanyByCompanyId(Integer companyId) {
        return modelMapper.map(companyRepository.findById(companyId)
                        .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId)),
                CompanyDTO.class);

    }

    @Override
    public List<CompanyDTO> getAllCompany() {
        List<Company> companyList = companyRepository.findAll();
        return companyList.stream().map(company -> modelMapper.map(company, CompanyDTO.class)).toList();
    }

    @Transactional
    @Override
    public CompanyDTO updateTheCompanyById(Integer companyId, CompanyDTO companyDTO) {

        Company existingCompany = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));
        existingCompany.setName(companyDTO.getName());
        existingCompany.setDescription(companyDTO.getDescription());
        existingCompany.setWebsiteUrl(companyDTO.getWebsiteUrl());
        existingCompany.setEmail(companyDTO.getEmail());
        existingCompany.setPhoneNumber(companyDTO.getPhoneNumber());
        existingCompany.setAddress(companyDTO.getAddress());
        existingCompany.setIndustry(companyDTO.getIndustry());
        existingCompany.setFoundedYear(companyDTO.getFoundedYear());
        existingCompany.setEmployeeCount(companyDTO.getEmployeeCount());
        System.out.println(companyDTO.getAddress() + "-------------");
        existingCompany.setActive(companyDTO.getIsActive());
        System.out.println(existingCompany.isActive() + "-------------");
        Company updatedCompany = companyRepository.save(existingCompany);
        return modelMapper.map(updatedCompany, CompanyDTO.class);
    }

    @Override
    public CompanyDTO partialUpdateCompany(int companyId, HashMap<String, Object> updatedCompanyDetails) {
        log.info("Fetching Company based on company id:-{}", companyId);

        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new ResourceNotFoundException("Company Not Found With ID :-" + companyId)
        );

        try {
            log.debug("Applying partial update with data: {}", updatedCompanyDetails);
            objectMapper.updateValue(company, updatedCompanyDetails);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("Invalid update data provided for company id{} : {}", companyId, illegalArgumentException.getMessage());
            throw new IllegalArgumentException("Invalid UpdatedData: " + illegalArgumentException.getMessage(), illegalArgumentException);
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating company id {}: {}", companyId, e.getMessage());
            throw new RuntimeException("Unexpected error occurred during the update operation", e);
        }

        Company updatedCompany = companyRepository.save(company);
        log.info("Successfully updated Company Id :-{}", companyId);

        return modelMapper.map(updatedCompany, CompanyDTO.class);
    }

    @Override
    public void deleteCompany(int companyId) {

        Company company = companyRepository.findById(companyId).orElseThrow(() ->
                new ResourceNotFoundException("Company Not Found With ID :-" + companyId)
        );
        companyRepository.deleteById(companyId);
    }

}
