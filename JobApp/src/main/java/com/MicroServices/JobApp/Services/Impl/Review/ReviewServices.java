package com.MicroServices.JobApp.Services.Impl.Review;

import com.MicroServices.JobApp.Dto.Review.ReviewDTO;

import java.util.List;

public interface ReviewServices {
    ReviewDTO addReview(Integer companyId, ReviewDTO reviewDTO);

    List<ReviewDTO> getAllReviewsBasedOnCompanyId(Integer companyId);

    List<ReviewDTO> getAllReviewsBasedOnReviewIdAndCompanyId(Integer companyId, Integer reviewId);

    ReviewDTO updateReviewBasedOnCompanyAndReviewId(Integer companyId, Integer reviewId, ReviewDTO reviewDTO);

    void deleteByIdAndCompanyId(Integer companyId, Integer reviewId);
}
