package io.github.pedrozaz.dataloaderservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/jobs")
@RequiredArgsConstructor
@Slf4j
public class BatchJobController {
    private final JobLauncher jobLauncher;
    private final Job historicalDataLoadJob;

    @PostMapping("/start/historical-load")
    public ResponseEntity<String> runHistoricalLoadJob() {
        try {
            log.info("Received request to start historical data load job");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(historicalDataLoadJob, jobParameters);

            log.info("Started historical data load job");
            return ResponseEntity.ok("Started historical data load job");
        } catch (Exception e) {
            log.error("Error starting historical data load job", e);
            return ResponseEntity.status(500)
                    .body("Error starting historical data load job: " + e.getMessage());
        }
    }
}
