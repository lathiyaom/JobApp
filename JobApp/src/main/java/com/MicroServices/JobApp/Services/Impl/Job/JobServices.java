package com.MicroServices.JobApp.Services.Impl.Job;

import com.MicroServices.JobApp.Dto.Job.JobDTO;
import com.MicroServices.JobApp.Dto.Job.JobFilter;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface JobServices {
    JobDTO saveJob(JobDTO jobDTO);

    JobDTO getJobByJobId(int jobId);

    List<JobDTO> getAllJobs();


    void deleteJobByJobId(int jobId);

    JobDTO updateJobByJobId(JobDTO jobDTO, int jobId);

    JobDTO partialUpdateJob(int jobId, HashMap<String, Object> updatedJobDetails);

    Page<JobDTO> getJobs(int pageNo, int pageSize);

    List<JobDTO> getJobsBasedOnFilter(JobFilter filter);
}
