package com.MicroServices.JobApp.Services.Impl.Job;

import com.MicroServices.JobApp.Dto.Job.JobDTO;
import com.MicroServices.JobApp.Dto.Job.JobFilter;
import com.MicroServices.JobApp.Entity.Job;
import com.MicroServices.JobApp.Exceptions.ResourceNotFoundException;
import com.MicroServices.JobApp.Repository.JobRepository;
import com.MicroServices.JobApp.Utils.JobSpecification;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class JobServicesImpl implements JobServices {

    private static final Logger log = LoggerFactory.getLogger(JobServicesImpl.class);
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    // ✅ Use constructor injection (preferred over @Autowired on fields)
    public JobServicesImpl(JobRepository jobRepository, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public JobDTO saveJob(JobDTO jobDTO) {
        Job job = modelMapper.map(jobDTO, Job.class);
        if (log.isDebugEnabled()) {
            log.debug("The data is: {}", job);
        }

        Job savedJob = jobRepository.save(job);

        if (log.isDebugEnabled()) {
            log.debug("The saved data is: {}", savedJob);
        }

        return modelMapper.map(savedJob, JobDTO.class);
    }

    @Override
    public JobDTO getJobByJobId(int jobId) {
        log.info("Fetching Job Details for JobId: {}", jobId);

        return modelMapper.map(
                jobRepository.findById(jobId)
                        .orElseThrow(() -> new ResourceNotFoundException("Job not found with ID: " + jobId)),
                JobDTO.class
        );
    }

    @Override
    public List<JobDTO> getAllJobs() {
        log.info("Fetching all jobs...");

        List<Job> jobList = jobRepository.findAll();

        if (jobList.isEmpty()) {
            log.warn("No jobs found in the database.");
            return List.of(); // ✅ concise & unmodifiable
        }

        // ✅ Use Java 16+ Stream.toList() for unmodifiable list
        List<JobDTO> jobDTOList = jobList.stream()
                .map(job -> modelMapper.map(job, JobDTO.class))
                .toList();

        log.info("Successfully fetched {} job(s)", jobDTOList.size());
        return jobDTOList;
    }
    @Async
    public void deleteJobByJobId(int jobId) {
        log.info("Fetching job for jobId :- {}", jobId);
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isEmpty()) {
            throw new ResourceNotFoundException("This jobId Related data is not Found into database :-" + jobId);
        }
        jobRepository.deleteById(jobId);
    }

    @Override
    public JobDTO updateJobByJobId(JobDTO jobDTO, int jobId) {
        log.info("Fetching job for jobId :- {}", jobDTO);
        Optional<Job> job = jobRepository.findById(jobId);
        if (job.isEmpty()) {
            throw new ResourceNotFoundException("This jobId Related data is not Found into database :-" + jobId);
        }
        Job updatedJob = job.get();
        updatedJob.setActive(jobDTO.getIsActive());
        updatedJob.setContactEmail(jobDTO.getContactEmail());
        updatedJob.setDeadline(jobDTO.getDeadline());
        updatedJob.setLocation(jobDTO.getLocation());
        updatedJob.setEmploymentType(jobDTO.getEmploymentType());
        updatedJob.setExperienceRequired(jobDTO.getExperienceRequired());
        updatedJob.setPostedDate(jobDTO.getPostedDate());
        updatedJob.setTitle(jobDTO.getTitle());
        jobRepository.save(updatedJob);
        return modelMapper.map(updatedJob, JobDTO.class);
    }

    @Override
    public JobDTO partialUpdateJob(int jobId, HashMap<String, Object> updatedJobDetails) {

        log.info("Fetching Job based on job id:-{}", jobId);
        Job job = jobRepository.findById(jobId).orElseThrow(() -> new ResourceNotFoundException("Job Not Found With ID :-" + jobId));
        try {
            log.debug("Applying partial update with data: {}", updatedJobDetails);
            objectMapper.updateValue(job, updatedJobDetails);
        } catch (IllegalArgumentException illegalArgumentException) {
            log.error("Invalid update data provided for job id{} : {}", jobId, illegalArgumentException.getMessage());
            throw new IllegalArgumentException("Invalid UpdatedData: " + illegalArgumentException.getMessage(), illegalArgumentException);
        } catch (JsonMappingException e) {
            log.error("Failed to map update data to Job Enitity for Id{} :- {}", jobId, e.getMessage());
            throw new RuntimeException(e);
        }
        Job updatedJob = jobRepository.save(job);
        log.info("Successfully updated Job Id :-{}", jobId);
        return modelMapper.map(updatedJob, JobDTO.class);
    }

    @Override
    public Page<JobDTO> getJobs(int pageNo, int pageSize) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Job> jobPage = jobRepository.findAll(pageable);
        return jobPage.map(job -> modelMapper.map(job, JobDTO.class));
    }

    @Override
    public List<JobDTO> getJobsBasedOnFilter(JobFilter filter) {

        Specification<Job> spec = Specification.where(null);
        if (filter.getTitle() != null) {
            spec = spec.and(JobSpecification.hasTitle(filter.getTitle()));
        }
        if (filter.getEmploymentType() != null) {
            spec = spec.and(JobSpecification.hasEmploymentType(filter.getEmploymentType()));
        }
        if (filter.getLocation() != null) {
            spec = spec.and(JobSpecification.hasLocationLike(filter.getLocation()));
        }
        if (filter.getExperienceRequiredGte() != null) {
            spec = spec.and(JobSpecification.experienceGreaterThanOrEqual(filter.getExperienceRequiredGte()));
        }
        if (filter.getExperienceRequiredLte() != null) {
            spec = spec.and(JobSpecification.experienceLessThanOrEqual(filter.getExperienceRequiredLte()));
        }
        if (filter.getPostedDateAfter() != null) {
            spec = spec.and(JobSpecification.postedDateAfter(filter.getPostedDateAfter()));
        }
        if (filter.getDeadlineBefore() != null) {
            spec = spec.and(JobSpecification.deadlineBefore(filter.getDeadlineBefore()));
        }
        List<Job> jobList = jobRepository.findAll(spec);
        return jobList.stream().map(job -> modelMapper.map(job, JobDTO.class)).toList();
    }
}
