package com.MicroServices.JobApp.Dto.Job;

import com.MicroServices.JobApp.Dto.Company.CompanyDTO;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class JobDTO {

    private Long id;
    private String title;
    private String employmentType;
    private Integer experienceRequired;
    private String location;
    private LocalDate postedDate;
    private LocalDate deadline;
    private String contactName;
    private String contactEmail;

    @JsonProperty("isActive")
    private boolean isActive;

    // Add the CompanyDTO to hold company-related information
    private CompanyDTO company;

    public JobDTO() {
    }

    public JobDTO(Long id, String title, String employmentType, Integer experienceRequired,
                  String location, LocalDate postedDate, LocalDate deadline,
                  String contactName, String contactEmail, boolean isActive, CompanyDTO company) {
        this.id = id;
        this.title = title;
        this.employmentType = employmentType;
        this.experienceRequired = experienceRequired;
        this.location = location;
        this.postedDate = postedDate;
        this.deadline = deadline;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.isActive = isActive;
        this.company = company;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(Integer experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDate postedDate) {
        this.postedDate = postedDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", employmentType='" + employmentType + '\'' +
                ", experienceRequired=" + experienceRequired +
                ", location='" + location + '\'' +
                ", postedDate=" + postedDate +
                ", deadline=" + deadline +
                ", contactName='" + contactName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", isActive=" + isActive +
                ", company=" + company +
                '}';
    }
}
