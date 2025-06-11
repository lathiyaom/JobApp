package com.MicroServices.JobApp.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Job entity for storing job details.
 */
@Builder
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity implements Serializable {

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(name = "employment_type", nullable = false)
    private String employmentType;

    @Column(name = "experience_required", nullable = false)
    private Integer experienceRequired;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "posted_date", nullable = false)
    private LocalDate postedDate;

    @Column(name = "deadline")
    private LocalDate deadline;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return isActive == job.isActive && Objects.equals(title, job.title) && Objects.equals(employmentType, job.employmentType) && Objects.equals(experienceRequired, job.experienceRequired) && Objects.equals(location, job.location) && Objects.equals(postedDate, job.postedDate) && Objects.equals(deadline, job.deadline) && Objects.equals(contactName, job.contactName) && Objects.equals(contactEmail, job.contactEmail) && Objects.equals(company, job.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, employmentType, experienceRequired, location, postedDate, deadline, contactName, contactEmail, isActive, company);
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Job() {
    }

    public Job(String title, String employmentType, Integer experienceRequired, String location, LocalDate postedDate, LocalDate deadline, String contactName, String contactEmail, boolean isActive, Company company) {
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

    @Override
    public String toString() {
        return "Job{" +
                "title='" + title + '\'' +
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
