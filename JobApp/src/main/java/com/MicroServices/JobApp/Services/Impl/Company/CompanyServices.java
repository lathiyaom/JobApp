package com.MicroServices.JobApp.Services.Impl.Company;

import com.MicroServices.JobApp.Dto.Company.CompanyDTO;

import java.util.HashMap;
import java.util.List;

public interface CompanyServices {
    CompanyDTO saveCompany(CompanyDTO companyDTO);

    CompanyDTO getCompanyByCompanyId(Integer companyId);

    List<CompanyDTO> getAllCompany();

    CompanyDTO updateTheCompanyById(Integer companyId, CompanyDTO companyDTO);

    CompanyDTO partialUpdateCompany(int companyId, HashMap<String, Object> updatedCompanyDetails);

    void deleteCompany(int companyId);
}
