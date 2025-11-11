package io.github.pedrozaz.dataloaderservice.config;

import io.github.pedrozaz.dataloaderservice.model.Circuit;
import io.github.pedrozaz.dataloaderservice.repository.CircuitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class HistoricalDataBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final CircuitRepository circuitRepository;

    @Bean
    public FlatFileItemReader<Circuit> circuitReader() {
        log.info("Initializing circuitReader...");
        return new FlatFileItemReaderBuilder<Circuit>()
                .name("circuitItemReader")
                .resource(new ClassPathResource("data/circuits.csv"))
                .delimited()
                .names(new String[]{
                        "circuitId", "circuitRef", "name", "location", "country",
                        "latitude", "longitude", "altitude"
                })
                .linesToSkip(1)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Circuit>() {{
                    setTargetType(Circuit.class);
                }})
                .build();
    }

    @Bean
    public RepositoryItemWriter<Circuit> circuitWriter() {
        log.info("Initializing circuitWriter...");
        return new RepositoryItemWriterBuilder<Circuit>()
                .repository(circuitRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step loadCircuitsStep(FlatFileItemReader<Circuit> circuitReader,
                                 RepositoryItemWriter<Circuit> circuitWriter) {
        log.info("Initializing loadCircuitsStep...");
        return new StepBuilder("loadCircuitsStep", jobRepository)
                .<Circuit, Circuit>chunk(10, transactionManager)
                .reader(circuitReader)
                .writer(circuitWriter)
                .build();
    }

    @Bean
    public Job historicalDataLoadJob(Step loadCircuitsStep) {
        log.info("Initializing historicalDataLoadJob...");
        return new JobBuilder("historicalDataLoadJob", jobRepository)
                .start(loadCircuitsStep)
                .build();
    }
}
