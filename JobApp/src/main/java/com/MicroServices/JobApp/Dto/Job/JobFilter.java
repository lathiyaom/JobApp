package com.MicroServices.JobApp.Dto.Job;

// JobFilter.java

import java.time.LocalDate;

public class JobFilter {

    private String title;
    private String employmentType;
    private String location;
    private Integer experienceRequiredGte;
    private Integer experienceRequiredLte;
    private LocalDate postedDateAfter;
    private LocalDate deadlineBefore;

    // Getters and Setters

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getExperienceRequiredGte() {
        return experienceRequiredGte;
    }

    public void setExperienceRequiredGte(Integer experienceRequiredGte) {
        this.experienceRequiredGte = experienceRequiredGte;
    }

    public Integer getExperienceRequiredLte() {
        return experienceRequiredLte;
    }

    public void setExperienceRequiredLte(Integer experienceRequiredLte) {
        this.experienceRequiredLte = experienceRequiredLte;
    }

    public LocalDate getPostedDateAfter() {
        return postedDateAfter;
    }

    public void setPostedDateAfter(LocalDate postedDateAfter) {
        this.postedDateAfter = postedDateAfter;
    }

    public LocalDate getDeadlineBefore() {
        return deadlineBefore;
    }

    public void setDeadlineBefore(LocalDate deadlineBefore) {
        this.deadlineBefore = deadlineBefore;
    }

    public JobFilter() {
    }

    public JobFilter(String title, String employmentType, String location, Integer experienceRequiredGte, Integer experienceRequiredLte, LocalDate postedDateAfter, LocalDate deadlineBefore) {
        this.title = title;
        this.employmentType = employmentType;
        this.location = location;
        this.experienceRequiredGte = experienceRequiredGte;
        this.experienceRequiredLte = experienceRequiredLte;
        this.postedDateAfter = postedDateAfter;
        this.deadlineBefore = deadlineBefore;
    }
}
