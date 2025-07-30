package com.MicroServices.JobApp.Controller.Job;

import com.MicroServices.JobApp.Constant.HttpStatusCodeEnum;
import com.MicroServices.JobApp.Dto.Job.JobDTO;
import com.MicroServices.JobApp.Dto.Job.JobFilter;
import com.MicroServices.JobApp.Helper.SuccessResponse;
import com.MicroServices.JobApp.Services.Impl.Job.JobServicesImpl;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * JobController handles job-related operations such as creation, retrieval, update, deletion, and filtering.
 * <p>
 * Role-Based Access Control:
 * - Roles required: ADMIN, USER
 * - Users with 'ADMIN' role have full access (create, update, delete jobs).
 * - Users with 'USER' role can view jobs (get job by ID, get all jobs, get filtered jobs).
 * <p>
 * Endpoints:
 * - POST /addjob: Creates a new job (ADMIN role required).
 * - GET /getJob/{jobId}: Fetches a job by its ID (USER role required).
 * - GET /getAlljobs: Fetches all jobs (USER role required).
 * - PUT /updatejob/{jobId}: Updates an existing job by ID (ADMIN role required).
 * - PATCH /updatejobsomefilds/{jobId}: Partially updates a job (ADMIN role required).
 * - DELETE /deletejobById/{jobId}: Deletes a job by ID (ADMIN role required).
 * - GET /: Fetches a paginated list of jobs (USER role required).
 * - GET /filter: Filters jobs based on given criteria (USER role required).
 */
@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobServicesImpl jobServices;
    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    public JobController(JobServicesImpl jobServices) {
        this.jobServices = jobServices;
    }

    /**
     * Creates a new job.
     *
     * @param jobDTO The job data to be created.
     * @return ResponseEntity containing the created job data.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addjob")
    public ResponseEntity<Object> createJob(@RequestBody JobDTO jobDTO) {
        log.info("📥 Received request to create job: {}", jobDTO);
        log.debug("🔧 Calling service layer to save job...");
        JobDTO savedJob = jobServices.saveJob(jobDTO);
        log.info("✅ Job successfully saved with ID: {}", savedJob);
        SuccessResponse<JobDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.CREATED, "Job created successfully", savedJob, LocalDateTime.now());
        log.debug("📤 Returning success response...");
        return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
    }

    /**
     * Fetches a job by its ID.
     *
     * @param jobId The ID of the job to fetch.
     * @return ResponseEntity containing the job data.
     * <p>
     * Role Required: USER
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getJob/{jobId}")
    public ResponseEntity<Object> getJobByJobId(@PathVariable @Min(1) Integer jobId) {
        log.info("GetByJobId is calling and JobId is :- {}", jobId);
        log.debug("Calling services layer to get the job based on job id...");
        JobDTO jobDTO = jobServices.getJobByJobId(jobId);
        SuccessResponse<JobDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Job Found Successfully", jobDTO, LocalDateTime.now());
        log.debug("Returning success response...");
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Fetches all jobs.
     *
     * @return ResponseEntity containing a list of all jobs.
     * <p>
     * Role Required: USER
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getAlljobs")
    public ResponseEntity<Object> getAllJobs() {
        log.info("GetAll Jobs :-");
        log.debug("Calling Services layer for get all the jobs....");
        List<JobDTO> jobDTOList = jobServices.getAllJobs();
        log.debug("Returning success Response....");
        SuccessResponse<List<JobDTO>> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Jobs Found Successfully", jobDTOList, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(successResponse);
    }

    /**
     * Deletes a job by its ID.
     *
     * @param jobId The ID of the job to delete.
     * @return ResponseEntity indicating the deletion was successful.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deletejobById/{jobId}")
    public ResponseEntity<Object> deleteJobByJobId(@PathVariable @Min(1) int jobId) {
        log.info("Delete Jobs By JobId....");
        log.debug("Calling Services layer for delete job by jobId...");
        jobServices.deleteJobByJobId(jobId);
        SuccessResponse<String> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Job is deleted", null, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Updates a job by its ID.
     *
     * @param jobDTO The updated job data.
     * @param jobId  The ID of the job to update.
     * @return ResponseEntity containing the updated job data.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updatejob/{jobId}")
    public ResponseEntity<Object> updateJobByJobId(@RequestBody JobDTO jobDTO, @PathVariable @Min(1) int jobId) {
        log.info("Update The Job By JobId...");
        log.debug("Calling Services layer for update the job by jobId....");
        JobDTO updatedJobByJobId = jobServices.updateJobByJobId(jobDTO, jobId);
        SuccessResponse<JobDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Job updated successfully", updatedJobByJobId, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Partially updates a job.
     *
     * @param jobId             The ID of the job to update.
     * @param updatedJobDetails The fields to be updated.
     * @return ResponseEntity containing the updated job data.
     * <p>
     * Role Required: ADMIN
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/updatejobsomefilds/{jobId}")
    public ResponseEntity<Object> partiallyUpdateJob(@PathVariable int jobId, @RequestBody HashMap<String, Object> updatedJobDetails) {
        log.info("Patch request to partially update job with ID :-{}", jobId);
        JobDTO updateJobDto = jobServices.partialUpdateJob(jobId, updatedJobDetails);
        SuccessResponse<JobDTO> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Job partially updated successfully", updateJobDto, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Fetches a paginated list of jobs.
     *
     * @param pageNo   The page number to retrieve.
     * @param pageSize The number of jobs per page.
     * @return ResponseEntity containing a paginated list of jobs.
     * <p>
     * Role Required: USER
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping()
    public ResponseEntity<SuccessResponse<Page<JobDTO>>> getJobs(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        Page<JobDTO> jobDTOPage = jobServices.getJobs(pageNo, pageSize);
        SuccessResponse<Page<JobDTO>> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Jobs fetched successfully", jobDTOPage, LocalDateTime.now());
        return ResponseEntity.ok(successResponse);
    }

    /**
     * Fetches jobs based on provided filters.
     *
     * @param filter The filter criteria.
     * @return ResponseEntity containing the filtered job list.
     * <p>
     * Role Required: USER
     */
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/filter")
    public ResponseEntity<List<JobDTO>> getJobsBasedOnFilters(JobFilter filter) {
        List<JobDTO> jobDTOList = jobServices.getJobsBasedOnFilter(filter);
        SuccessResponse<List<JobDTO>> successResponse = new SuccessResponse<>(HttpStatusCodeEnum.OK, "Found jobs based on filters", jobDTOList, LocalDateTime.now());
        return ResponseEntity.ok(successResponse.getData());
    }
}
