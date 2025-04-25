package com.sia.batch.config;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.sia.batch.entity.Member;
import com.sia.batch.entity.MemberData;

import jakarta.persistence.EntityManagerFactory;

/**
 * job에서 실행되는 step -> 한개일 수도 여러 개일 수도 있음
 * 1. 데이터읽기(Extract) -> 2. 데이터 변환 (Transform) -> 3. DB에 저장(Load)
 */
@Configuration
public class TestStepV1Configuration {

	@Bean
	public Step testStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager
	) {
		return new StepBuilder("testStep", jobRepository)
			.tasklet(
				(contribution, chunkContext) -> {
					System.out.println("Hello World!");
					return RepeatStatus.FINISHED;
				},
				platformTransactionManager
			)
			.build();
	}

}
