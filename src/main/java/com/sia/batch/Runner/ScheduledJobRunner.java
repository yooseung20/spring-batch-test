package com.sia.batch.Runner;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.RequiredArgsConstructor;

/**
 * 스케줄링 된 job @EnableScheduling 어노테이션과 함께 사용
 */
@Configuration
@RequiredArgsConstructor
public class ScheduledJobRunner {

	private final JobLauncher jobLauncher;
	private final Job testJob;
	private final Job testJobV2;
	private final Job testJobV3;

	@Scheduled(cron = "0 0 * * * ?") // testJob: 매 정시
	public void runTestJob() throws Exception {
		JobParameters params = new JobParametersBuilder()
			.toJobParameters();
		jobLauncher.run(testJob, params);
	}

	@Scheduled(cron = "0 */5 * * * ?") // testJobV2: 5분마다
	public void runTestJobV2() throws Exception {
		JobParameters params = new JobParametersBuilder()
			.addString("dateTime", LocalDateTime.now().toString())
			.toJobParameters();
		jobLauncher.run(testJobV2, params);
	}

	@Scheduled(cron = "0 */3 * * * ?") // testJobV3: 3분마다
	public void runTestJobV3() throws Exception {
		JobParameters params = new JobParametersBuilder()
			.toJobParameters();
		jobLauncher.run(testJobV3, params);
	}
}