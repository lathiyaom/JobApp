package com.MicroServices.JobApp.Utils;

import com.MicroServices.JobApp.Entity.Job;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class JobSpecification {
    //Filter By title
    public static Specification<Job> hasTitle(String title) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("title"), title));
    }

    //Filter by employmentType
    public static Specification<Job> hasEmploymentType(String employmentType) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employmentType"), employmentType));
    }

    //Filter by location
    public static Specification<Job> hasLocationLike(String location) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("location"), "%" + location + "%"));
    }

    // Filter by experienceRequired (greater than or equal)
    public static Specification<Job> experienceGreaterThanOrEqual(Integer experience) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("experienceRequired"), experience);
    }

    //Filter by experiencesRequired(less than or equal)
    public static Specification<Job> experienceLessThanOrEqual(Integer experience) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("experienceRequired"), experience);
    }

    // Filter by postedDate (greater than or equal)
    public static Specification<Job> postedDateAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("postedDate"), date);
    }

    // Filter by deadline (before a specific date)
    public static Specification<Job> deadlineBefore(LocalDate date) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("deadline"), date);
    }
}
