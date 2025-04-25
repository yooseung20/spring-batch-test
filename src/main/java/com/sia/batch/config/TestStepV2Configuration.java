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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.sia.batch.entity.Member;
import com.sia.batch.entity.MemberData;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class TestStepV2Configuration {

	private static final int CHUNK_SIZE = 1000;

	@Bean
	public Step testStepV2(
		JobRepository jobRepository,
		PlatformTransactionManager platformTransactionManager,
		ItemReader<Member> memberItemReader,
		ItemProcessor<Member, MemberData> memberDataItemProcessor,
		ItemWriter<MemberData> memberDataItemWriter
	) {
		return new StepBuilder("testStepV2", jobRepository)
			.<Member, MemberData>chunk(CHUNK_SIZE, platformTransactionManager)
			.reader(memberItemReader)
			.processor(memberDataItemProcessor)
			.writer(memberDataItemWriter)
			.build();
	}

	@Bean
	public ItemReader<Member> memberItemReader(EntityManagerFactory entityManagerFactory) {
		return new JpaPagingItemReaderBuilder<Member>()
			.entityManagerFactory(entityManagerFactory)
			.name("memberItemReader")
			.pageSize(CHUNK_SIZE)
			.queryString("SELECT o FROM Member o ORDER BY o.id")
			.build();
	}

	@Bean
	@StepScope
	public ItemProcessor<Member, MemberData> memberDataItemProcessor(
		@Value("#{jobParameters['dateTime']}") String dateTime) {
		System.out.println("arg 잘 전달 되나요? : " + dateTime);

		return item -> new MemberData(
			UUID.randomUUID(),
			item.getId(),
			"Hello " + item.getName() + LocalDateTime.now()
		);
	}

	@Bean
	public ItemWriter<MemberData> memberDataItemWriter(EntityManagerFactory entityManagerFactory) {
		return new JpaItemWriterBuilder<MemberData>()
			.entityManagerFactory(entityManagerFactory)
			.usePersist(true)
			.build();
	}
}
