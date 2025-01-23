package com.onlinestationarymart.batch_service.config;

import com.onlinestationarymart.batch_service.dto.CSVData;
import com.onlinestationarymart.batch_service.prcoessor.CustomProcessor;
import com.onlinestationarymart.batch_service.writer.CustomItemWriter;
import com.onlinestationarymart.domain_service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {

    @Autowired
    CustomProcessor customProcessor;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchConfig.class);

    @Bean
    public FlatFileItemReader<CSVData> itemReader() {
    	LOGGER.info("Reading Data from the path");
        FlatFileItemReader<CSVData> reader = new FlatFileItemReader<>();
        reader.setResource(new PathResource("batch-service/src/main/resources/ordersData.csv"));
        reader.setName("csvReader");
        reader.setLinesToSkip(1); // Skip header line
        reader.setLineMapper(lineMapper());
        return reader;
    }

    private LineMapper<CSVData> lineMapper() {
    	LOGGER.info("Inside Line Mapper");
        DefaultLineMapper<CSVData> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setNames("userId", "fullName", "email", "address", "productCodeString", "productQuantityString");

        BeanWrapperFieldSetMapper<CSVData> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
        beanWrapperFieldSetMapper.setTargetType(CSVData.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
        return lineMapper;
    }

    @Bean
    public CustomProcessor itemProcessor() {
         return new CustomProcessor();
    }

    @Bean
    public CustomItemWriter itemWriter() {
        return new CustomItemWriter();
    }
    
	@Bean
    public Step step(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("kafkaStep", jobRepository)
        		.<CSVData,OrderDTO>chunk(10, platformTransactionManager)
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .taskExecutor(taskExecutor())
                .build();
    }
    
    @Bean
    public Job runJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("job", jobRepository)
                .flow(step(jobRepository, platformTransactionManager))
                .end()
                .build();
    }
    
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // Number of threads in the pool
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("Batch-Thread-");
        executor.initialize();
        return executor;
    }

}
