package com.MicroServices.JobApp.Controller.Review;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.MicroServices.JobApp.Dto.Review.ReviewDTO;
import com.MicroServices.JobApp.Helper.SuccessResponse;
import com.MicroServices.JobApp.Services.Impl.Review.ReviewServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ReviewController handles all the review-related endpoints for the application.
 * <p>
 * Endpoints:
 * - POST /company/{companyId}/addreview: Adds a new review for a company.
 * - GET /company/{companyId}/review: Fetches all reviews for a company.
 * - GET /company/{companyId}/review/{reviewId}: Fetches a specific review for a company by review ID.
 * - PUT /company/{companyId}/review/{reviewId}: Updates a specific review for a company by review ID.
 * - DELETE /company/{companyId}/review/{reviewId}: Deletes a specific review for a company by review ID.
 */
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private static final Logger log = LoggerFactory.getLogger(ReviewController.class);

    private final ReviewServices reviewServices;

    @Autowired
    public ReviewController(ReviewServices reviewServices) {
        this.reviewServices = reviewServices;
    }

    /**
     * Adds a new review for a company.
     *
     * @param companyId The ID of the company for which the review is being added.
     * @param reviewDTO The review data to be added.
     * @return ResponseEntity with success message and the saved review.
     */
    @PostMapping("/company/{companyId}/addreview")
    public ResponseEntity<Object> addReview(@PathVariable Integer companyId, @RequestBody ReviewDTO reviewDTO) {
        log.info("📥 Adding review for company with ID: {}", companyId);
        log.debug("Review details: {}", reviewDTO);
        ReviewDTO savedReviewDTO = reviewServices.addReview(companyId, reviewDTO);
        SuccessResponse<ReviewDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.CREATED, "Review added successfully", savedReviewDTO, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Fetches all reviews for a company.
     *
     * @param companyId The ID of the company whose reviews are being fetched.
     * @return ResponseEntity containing a list of reviews for the company.
     */
    @GetMapping("/company/{companyId}/review")
    public ResponseEntity<Object> getReviewsBasedOnCompanyId(@PathVariable Integer companyId) {
        log.info("📤 Fetching reviews for company with ID: {}", companyId);
        List<ReviewDTO> reviewDTOList = reviewServices.getAllReviewsBasedOnCompanyId(companyId);
        SuccessResponse<List<ReviewDTO>> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.FOUND, "Reviews fetched successfully", reviewDTOList, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Fetches a specific review for a company by review ID.
     *
     * @param companyId The ID of the company.
     * @param reviewId  The ID of the review to fetch.
     * @return ResponseEntity containing the specific review.
     */
    @GetMapping("/company/{companyId}/review/{reviewId}")
    private ResponseEntity<Object> getReviewBasedOnReviewIdAndCompanyId(@PathVariable Integer companyId, @PathVariable Integer reviewId) {
        log.info("📤 Fetching review for company with ID: {} and review ID: {}", companyId, reviewId);
        List<ReviewDTO> reviewDTOList = reviewServices.getAllReviewsBasedOnReviewIdAndCompanyId(companyId, reviewId);
        SuccessResponse<List<ReviewDTO>> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.FOUND, "Review fetched successfully", reviewDTOList, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Updates a specific review for a company by review ID.
     *
     * @param companyId The ID of the company.
     * @param reviewId  The ID of the review to update.
     * @param reviewDTO The updated review data.
     * @return ResponseEntity containing the updated review.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Only ADMIN can update reviews
    @PutMapping("/company/{companyId}/review/{reviewId}")
    private ResponseEntity<Object> updateReviewBasedOnIdAndCompanyId(@PathVariable Integer companyId, @PathVariable Integer reviewId, @RequestBody ReviewDTO reviewDTO) {
        log.info("✏️ Updating review for company with ID: {} and review ID: {}", companyId, reviewId);
        log.debug("Updated review details: {}", reviewDTO);
        ReviewDTO updatedReviewDTO = reviewServices.updateReviewBasedOnCompanyAndReviewId(companyId, reviewId, reviewDTO);
        SuccessResponse<ReviewDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.FOUND, "Review updated successfully", updatedReviewDTO, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Deletes a specific review for a company by review ID.
     *
     * @param companyId The ID of the company.
     * @param reviewId  The ID of the review to delete.
     * @return ResponseEntity indicating the deletion was successful.
     */
    @PreAuthorize("hasRole('ADMIN')")  // Only ADMIN can delete reviews
    @DeleteMapping("/company/{companyId}/review/{reviewId}")
    private ResponseEntity<Object> deleteReviewByIdAndCompanyId(@PathVariable Integer companyId, @PathVariable Integer reviewId) {
        log.info("🗑️ Deleting review for company with ID: {} and review ID: {}", companyId, reviewId);
        reviewServices.deleteByIdAndCompanyId(companyId, reviewId);
        SuccessResponse<ReviewDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.FOUND, "Review deleted successfully", null, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }
}
