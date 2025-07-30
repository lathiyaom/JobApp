package com.MicroServices.JobApp.Controller.Company;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.MicroServices.JobApp.Dto.Company.CompanyDTO;
import com.MicroServices.JobApp.Helper.SuccessResponse;
import com.MicroServices.JobApp.Services.Impl.Company.CompanyServices;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * Controller for managing company-related operations such as creation, updating, fetching, and deletion.
 * <p>
 * Role-based Access Control:
 * - Roles required: ADMIN, MANAGER
 * - Users with 'ADMIN' role have full access (create, update, delete, get).
 * - Users with 'MANAGER' role have limited access (view companies).
 * <p>
 * Endpoints:
 * - POST /addcompany: Creates a new company (ADMIN role required).
 * - GET /getCompany/{companyId}: Fetches a company by its ID (MANAGER role required).
 * - GET /getallCompany: Fetches all companies (MANAGER role required).
 * - PUT /updatecompany/{companyId}: Updates an existing company by ID (ADMIN role required).
 * - PATCH /updateCompanySomefileds/{companyId}: Partially updates a company (ADMIN role required).
 * - DELETE /delete/{companyId}: Deletes a company by ID (ADMIN role required).
 */
@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyServices companyServices;
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    public CompanyController(CompanyServices companyServices) {
        this.companyServices = companyServices;
    }

    /**
     * Creates a new company.
     *
     * @param companyDTO The company data to be saved.
     * @return ResponseEntity containing the created company data.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addcompany")
    public ResponseEntity<Object> createCompany(@RequestBody CompanyDTO companyDTO) {
        log.info("Received request to create the company: {}", companyDTO);
        CompanyDTO savedCompanyDTO = companyServices.saveCompany(companyDTO);
        SuccessResponse<CompanyDTO> companyDTOSuccessResponse = new SuccessResponse<>(
                HttpStatusCodeEnum.CREATED,
                "Company successfully saved",
                savedCompanyDTO,
                LocalDateTime.now()
        );
        log.debug("Returning Success Response....");
        return ResponseEntity.ok(companyDTOSuccessResponse);
    }

    /**
     * Fetches a company by its ID.
     *
     * @param companyId The company ID to search for.
     * @return ResponseEntity containing the company details.
     * <p>
     * Role Required: MANAGER
     */
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/getCompany/{companyId}")
    private ResponseEntity<Object> getCompanyByCompanyId(@PathVariable @Min(1) Integer companyId) {
        log.info("GetByCompanyId is calling and CompanyId is :-{}", companyId);
        log.debug("Calling Services layer to get the company based on company Id...");
        CompanyDTO companyDTO = companyServices.getCompanyByCompanyId(companyId);
        SuccessResponse<CompanyDTO> successResponse = new SuccessResponse<>(
                HttpStatusCodeEnum.FOUND,
                "Fetched the Company Based on CompanyId",
                companyDTO,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Fetches all companies.
     *
     * @return ResponseEntity containing a list of all companies.
     * <p>
     * Role Required: MANAGER
     */
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/getallCompany")
    private ResponseEntity<Object> getAllCompany() {
        log.info("GetAllCompany is calling...");
        log.debug("Calling Services layer to get all companies...");
        List<CompanyDTO> companyDTOS = companyServices.getAllCompany();
        SuccessResponse<List<CompanyDTO>> successResponse = new SuccessResponse<>(
                HttpStatusCodeEnum.FOUND,
                "Fetched all the companies",
                companyDTOS,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Updates an existing company by its ID.
     *
     * @param companyId  The company ID to update.
     * @param companyDTO The new company data.
     * @return ResponseEntity with the updated company details.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatecompany/{companyId}")
    private ResponseEntity<Object> updateCompanyBasedOnId(@PathVariable @Min(1) Integer companyId, @RequestBody CompanyDTO companyDTO) {
        log.info("Updating the Company with companyId: {}", companyId);
        log.debug("Calling Services layer for company update...");
        CompanyDTO updatedCompanyDTO = companyServices.updateTheCompanyById(companyId, companyDTO);
        SuccessResponse<CompanyDTO> successResponse = new SuccessResponse<>(
                HttpStatusCodeEnum.OK,
                "Company updated successfully",
                updatedCompanyDTO,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Partially updates a company.
     *
     * @param companyId             The company ID to update.
     * @param updatedCompanyDetails The fields to be updated.
     * @return ResponseEntity with the updated company data.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updateCompanySomefileds/{companyId}")
    private ResponseEntity<Object> partiallyUpdateCompany(@PathVariable int companyId, @RequestBody HashMap<String, Object> updatedCompanyDetails) {
        try {
            log.info("Patch Request to partially update company with ID:-{}", companyId);
            CompanyDTO companyDTO = companyServices.partialUpdateCompany(companyId, updatedCompanyDetails);
            SuccessResponse<CompanyDTO> successResponse = new SuccessResponse<>(
                    HttpStatusCodeEnum.OK,
                    "Company updated partially",
                    companyDTO,
                    LocalDateTime.now()
            );
            return ResponseEntity.ok(successResponse);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatusCodeEnum.INTERNAL_SERVER_ERROR, null);
        }
    }

    /**
     * Deletes a company by its ID.
     *
     * @param companyId The company ID to delete.
     * @return ResponseEntity indicating the deletion was successful.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{companyId}")
    private ResponseEntity<Object> deleteByCompanyId(@PathVariable int companyId) {
        companyServices.deleteCompany(companyId);
        SuccessResponse<CompanyDTO> successResponse = new SuccessResponse<>(
                HttpStatusCodeEnum.OK,
                "Company deleted successfully",
                null,
                LocalDateTime.now()
        );
        return ResponseEntity.ok(successResponse);
    }
}
