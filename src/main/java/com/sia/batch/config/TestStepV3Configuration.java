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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.sia.batch.entity.Member;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class TestStepV3Configuration {

	private static final int CHUNK_SIZE = 1000;

	@Bean
	public Step testStepV3(
		JobRepository jobRepository,
		PlatformTransactionManager platformTransactionManager,
		ItemReader<Member> memberReader,
		ItemProcessor<Member, Member> memberItemProcessor,
		ItemWriter<Member> memberItemWriter
	) {

		return new StepBuilder("testStepV3", jobRepository)
			.<Member, Member>chunk(CHUNK_SIZE, platformTransactionManager)
			.reader(memberReader)
			.processor(memberItemProcessor)
			.writer(memberItemWriter)
			.build();
	}

	@Bean
	public ItemReader<Member> memberReader(EntityManagerFactory entityManagerFactory) {
		return new JpaPagingItemReaderBuilder<Member>()
			.entityManagerFactory(entityManagerFactory)
			.name("memberItemReader")
			.pageSize(CHUNK_SIZE)
			.queryString("SELECT o FROM Member o ORDER BY o.id")
			.build();
	}

	@Bean
	public ItemProcessor<Member, Member> memberItemProcessor() {
		return member -> {
			member.changeMemo("현재시간 : " + LocalDateTime.now() + " 메모닷!");
			return member;
		};
	}

	@Bean
	public ItemWriter<Member> memberItemWriter(EntityManagerFactory entityManagerFactory) {
		return new JpaItemWriterBuilder<Member>()
			.entityManagerFactory(entityManagerFactory)
			.usePersist(false)
			.build();
	}
}
