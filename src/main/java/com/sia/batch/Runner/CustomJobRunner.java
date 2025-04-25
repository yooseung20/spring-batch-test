package com.sia.batch.Runner;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * application 실행과 동시에 실행되야하는 job
 */
@Component
public class CustomJobRunner implements ApplicationRunner {

	private final JobLauncher jobLauncher;
	private final Job testJob;
	private final Job testJobV2;

	public CustomJobRunner(JobLauncher jobLauncher, Job testJob, Job testJobV2) {
		this.jobLauncher = jobLauncher;
		this.testJob = testJob;
		this.testJobV2 = testJobV2;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		JobParameters jobParameters = new JobParametersBuilder()
			.addString("dateTime", LocalDateTime.now().toString())
			.toJobParameters();

		jobLauncher.run(testJob, jobParameters);
		jobLauncher.run(testJobV2, jobParameters);
	}
}