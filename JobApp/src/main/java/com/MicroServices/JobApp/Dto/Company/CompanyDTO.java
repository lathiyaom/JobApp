package com.MicroServices.JobApp.Dto.Company;

import com.MicroServices.JobApp.Dto.Job.JobDTO;
import com.MicroServices.JobApp.Utils.Annotation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class CompanyDTO {
    private Long id;

    @NotBlank(message = "Company name is mandatory")
    private String name;

    private String description;

    @Pattern(
            regexp = "^(https?:\\/\\/)?([\\w\\-])+\\.[\\w\\-]+(\\.[\\w\\-]+)*(\\/[\\w\\-.,@?^=%&:/~+#]*)?$",
            message = "Invalid website URL"
    )
    private String websiteUrl;

    @ValidEmail
    private String email;

    @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    )
    private String phoneNumber;

    private String address;
    private String industry;
    private Integer foundedYear;

    @Min(value = 1, message = "Employee count must be at least 1")
    private Integer employeeCount;

    private boolean isActive;
    @JsonIgnore
    private List<JobDTO> jobList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Company name is mandatory") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Company name is mandatory") String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public @Pattern(
            regexp = "^(https?:\\/\\/)?([\\w\\-])+\\.[\\w\\-]+(\\.[\\w\\-]+)*(\\/[\\w\\-.,@?^=%&:/~+#]*)?$",
            message = "Invalid website URL"
    ) String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(@Pattern(
            regexp = "^(https?:\\/\\/)?([\\w\\-])+\\.[\\w\\-]+(\\.[\\w\\-]+)*(\\/[\\w\\-.,@?^=%&:/~+#]*)?$",
            message = "Invalid website URL"
    ) String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    ) String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Pattern(
            regexp = "^[+]?[0-9]{10,15}$",
            message = "Invalid phone number"
    ) String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Integer getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(Integer foundedYear) {
        this.foundedYear = foundedYear;
    }

    public @Min(value = 1, message = "Employee count must be at least 1") Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(@Min(value = 1, message = "Employee count must be at least 1") Integer employeeCount) {
        this.employeeCount = employeeCount;
    }


    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<JobDTO> getJobList() {
        return jobList;
    }

    public void setJobList(List<JobDTO> jobList) {
        this.jobList = jobList;
    }

    public CompanyDTO() {
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", industry='" + industry + '\'' +
                ", foundedYear=" + foundedYear +
                ", employeeCount=" + employeeCount +
                ", isActive=" + isActive +
                ", jobList=" + jobList +
                '}';
    }

    public CompanyDTO(Long id, String name, String description, String websiteUrl, String email, String phoneNumber, String address, String industry, Integer foundedYear, Integer employeeCount, boolean isActive, List<JobDTO> jobList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.websiteUrl = websiteUrl;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.industry = industry;
        this.foundedYear = foundedYear;
        this.employeeCount = employeeCount;
        this.isActive = isActive;
        this.jobList = jobList;
    }
}
