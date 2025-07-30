package com.MicroServices.JobApp.Entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Review extends BaseEntity implements Serializable {

    @Column(nullable = false)
    private String reviewerName;

    @Column(nullable = false)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Override
    public String toString() {
        return "Review{" +
                "reviewerName='" + reviewerName + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", company=" + company +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewerName, review.reviewerName) && Objects.equals(rating, review.rating) && Objects.equals(comment, review.comment) && Objects.equals(company, review.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewerName, rating, comment, company);
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Review() {
    }

    public Review(String reviewerName, Integer rating, String comment, Company company) {
        this.reviewerName = reviewerName;
        this.rating = rating;
        this.comment = comment;
        this.company = company;
    }
}
