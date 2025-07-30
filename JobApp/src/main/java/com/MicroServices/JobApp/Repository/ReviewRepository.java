package com.MicroServices.JobApp.Repository;

import com.MicroServices.JobApp.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByCompanyId(Integer companyId);

    List<Review> findByCompanyIdAndId(Integer companyId, Integer id);

    void deleteByCompanyIdAndId(Integer companyId, Integer reviewId);
}
