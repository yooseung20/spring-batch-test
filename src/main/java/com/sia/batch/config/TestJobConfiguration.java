package com.sia.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestJobConfiguration {

	@Bean
	public Job testJob(JobRepository jobRepository, Step testStep) {
		return new JobBuilder("testJob", jobRepository)
			.start(testStep)
			.build();
	}

	@Bean
	public Job testJobV2(JobRepository jobRepository, Step testStepV2) {
		return new JobBuilder("testJobV2", jobRepository)
			.start(testStepV2)
			.build();
	}

	@Bean
	public Job testJobV3(JobRepository jobRepository, Step testStepV3) {
		return new JobBuilder("testJobV3", jobRepository)
			.start(testStepV3)
			.build();
	}


}
