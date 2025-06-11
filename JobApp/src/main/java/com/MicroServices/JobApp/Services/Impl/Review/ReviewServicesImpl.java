package com.MicroServices.JobApp.Services.Impl.Review;

import com.MicroServices.JobApp.Dto.Review.ReviewDTO;
import com.MicroServices.JobApp.Entity.Company;
import com.MicroServices.JobApp.Entity.Review;
import com.MicroServices.JobApp.Exceptions.ResourceNotFoundException;
import com.MicroServices.JobApp.Repository.CompanyRepository;
import com.MicroServices.JobApp.Repository.ReviewRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServicesImpl implements ReviewServices {

    @Autowired
    private ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final ReviewRepository reviewRepository;

    public ReviewServicesImpl(CompanyRepository companyRepository, ReviewRepository reviewRepository, ModelMapper modelMapper) {
        this.companyRepository = companyRepository;
        this.reviewRepository = reviewRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReviewDTO addReview(Integer companyId, ReviewDTO reviewDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setCompany(company);
        reviewRepository.save(review);
        return modelMapper.map(review, ReviewDTO.class);
    }

    @Override
    public List<ReviewDTO> getAllReviewsBasedOnCompanyId(Integer companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));
        List<Review> reviewList = reviewRepository.findByCompanyId(companyId);

        return reviewList.stream().map(review -> modelMapper.map(review, ReviewDTO.class)).toList();
    }

    @Override
    public List<ReviewDTO> getAllReviewsBasedOnReviewIdAndCompanyId(Integer companyId, Integer reviewId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));

        List<Review> reviewList = reviewRepository.findByCompanyIdAndId(companyId, reviewId);
        return reviewList.stream().map(rev -> modelMapper.map(rev, ReviewDTO.class)).toList();
    }

    @Override
    public ReviewDTO updateReviewBasedOnCompanyAndReviewId(Integer companyId, Integer reviewId, ReviewDTO reviewDTO) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));
        Review existdReview = reviewRepository.findById(reviewId).get();
        existdReview.setCompany(company);
        System.out.println(review.getReviewerName() + "---------");
        existdReview.setReviewerName(reviewDTO.getReviewerName());
        System.out.println(existdReview.getReviewerName() + "++++++");
        existdReview.setComment(reviewDTO.getComment());
        existdReview.setRating(reviewDTO.getRating());
        reviewRepository.save(existdReview);
        return modelMapper.map(existdReview, ReviewDTO.class);
    }
    @Transactional
    @Override
    public void deleteByIdAndCompanyId(Integer companyId, Integer reviewId) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with ID: " + companyId));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Review not found with ID: " + reviewId));

        reviewRepository.deleteByCompanyIdAndId(companyId, reviewId);
    }
}
