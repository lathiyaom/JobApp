package com.MicroServices.JobApp.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Company entity representing job posting organization.
 */
@Entity
public class Company extends BaseEntity implements Serializable {
    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "industry")
    private String industry;

    @Column(name = "founded_year")
    private Integer foundedYear;

    @Column(name = "employee_count")
    private Integer employeeCount;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true; // Used for soft deletion or visibility

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Job> jobList;

    @OneToMany(
            mappedBy = "company",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Review> reviewList;

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return isActive == company.isActive && Objects.equals(name, company.name) && Objects.equals(description, company.description) && Objects.equals(websiteUrl, company.websiteUrl) && Objects.equals(email, company.email) && Objects.equals(phoneNumber, company.phoneNumber) && Objects.equals(address, company.address) && Objects.equals(industry, company.industry) && Objects.equals(foundedYear, company.foundedYear) && Objects.equals(employeeCount, company.employeeCount) && Objects.equals(jobList, company.jobList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, websiteUrl, email, phoneNumber, address, industry, foundedYear, employeeCount, isActive, jobList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
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

    public Integer getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(Integer employeeCount) {
        this.employeeCount = employeeCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public Company() {
    }

    public Company(String name, String description, String websiteUrl, String email, String phoneNumber, String address, String industry, Integer foundedYear, Integer employeeCount, boolean isActive, List<Job> jobList) {
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
