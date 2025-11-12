package io.github.pedrozaz.dataloaderservice.config;

import io.github.pedrozaz.dataloaderservice.model.*;
import io.github.pedrozaz.dataloaderservice.repository.*;
import lombok.RequiredArgsConstructor;
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
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.validation.BindException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
public class HistoricalDataBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    // Repositórios
    private final CircuitRepository circuitRepository;
    private final SeasonRepository seasonRepository;
    private final StatusRepository statusRepository;
    private final ConstructorRepository constructorRepository;
    private final DriverRepository driverRepository;

    private <T> T parseField(FieldSet fs, String fieldName, Class<T> type) {
        String value = fs.readString(fieldName);
        if (value == null || value.equals("\\N") || value.isEmpty()) {
            return null;
        }

        try {
            if (type == Integer.class) {
                return type.cast(Integer.parseInt(value));
            }
            if (type == Long.class) {
                return type.cast(Long.parseLong(value));
            }
            if (type == Float.class) {
                return type.cast(Float.parseFloat(value));
            }
            if (type == LocalDate.class) {
                return type.cast(LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE));
            }
            return type.cast(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // --- 1. Circuits ---

    @Bean
    public FlatFileItemReader<Circuit> circuitReader() {
        return new FlatFileItemReaderBuilder<Circuit>()
                .name("circuitItemReader")
                .resource(new ClassPathResource("data/circuits.csv"))
                .delimited()
                .names(new String[]{ "circuitId", "circuitRef", "name", "location", "country", "latitude", "longitude", "altitude", "url" })
                .linesToSkip(1)
                .fieldSetMapper(new FieldSetMapper<Circuit>() {
                    @Override
                    public Circuit mapFieldSet(FieldSet fs) throws BindException {
                        Circuit c = new Circuit();
                        c.setCircuitId(parseField(fs, "circuitId", Long.class));
                        c.setCircuitRef(parseField(fs, "circuitRef", String.class));
                        c.setName(parseField(fs, "name", String.class));
                        c.setLocation(parseField(fs, "location", String.class));
                        c.setCountry(parseField(fs, "country", String.class));
                        c.setLatitude(parseField(fs, "latitude", Float.class));
                        c.setLongitude(parseField(fs, "longitude", Float.class));
                        c.setAltitude(parseField(fs, "altitude", Integer.class));
                        c.setUrl(parseField(fs, "url", String.class));
                        return c;
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemWriter<Circuit> circuitWriter() {
        return new RepositoryItemWriterBuilder<Circuit>()
                .repository(circuitRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step loadCircuitsStep(FlatFileItemReader<Circuit> circuitReader,
                                 RepositoryItemWriter<Circuit> circuitWriter) {
        return new StepBuilder("loadCircuitsStep", jobRepository)
                .<Circuit, Circuit>chunk(10, transactionManager)
                .reader(circuitReader)
                .writer(circuitWriter)
                .build();
    }

    // --- 2. Seasons ---

    @Bean
    public FlatFileItemReader<Season> seasonReader() {
        return new FlatFileItemReaderBuilder<Season>()
                .name("seasonItemReader")
                .resource(new ClassPathResource("data/seasons.csv"))
                .delimited()
                .names(new String[]{"year", "url"})
                .linesToSkip(1)
                .fieldSetMapper(new FieldSetMapper<Season>() {
                    @Override
                    public Season mapFieldSet(FieldSet fs) throws BindException {
                        Season s = new Season();
                        s.setYear(parseField(fs, "year", Integer.class));
                        s.setUrl(parseField(fs, "url", String.class));
                        return s;
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemWriter<Season> seasonWriter() {
        return new RepositoryItemWriterBuilder<Season>()
                .repository(seasonRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step loadSeasonsStep(FlatFileItemReader<Season> seasonReader,
                                RepositoryItemWriter<Season> seasonWriter) {
        return new StepBuilder("loadSeasonsStep", jobRepository)
                .<Season, Season>chunk(10, transactionManager)
                .reader(seasonReader)
                .writer(seasonWriter)
                .build();
    }

    // --- 3. Status ---

    @Bean
    public FlatFileItemReader<Status> statusReader() {
        return new FlatFileItemReaderBuilder<Status>()
                .name("statusItemReader")
                .resource(new ClassPathResource("data/status.csv"))
                .delimited()
                .names(new String[]{"statusId", "status"})
                .linesToSkip(1)
                .fieldSetMapper(new FieldSetMapper<Status>() {
                    @Override
                    public Status mapFieldSet(FieldSet fs) throws BindException {
                        Status s = new Status();
                        s.setStatusId(parseField(fs, "statusId", Long.class));
                        s.setStatus(parseField(fs, "status", String.class));
                        return s;
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemWriter<Status> statusWriter() {
        return new RepositoryItemWriterBuilder<Status>()
                .repository(statusRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step loadStatusStep(FlatFileItemReader<Status> statusReader,
                               RepositoryItemWriter<Status> statusWriter) {
        return new StepBuilder("loadStatusStep", jobRepository)
                .<Status, Status>chunk(10, transactionManager)
                .reader(statusReader)
                .writer(statusWriter)
                .build();
    }

    // --- 4. Constructors ---

    @Bean
    public FlatFileItemReader<Constructor> constructorReader() {
        return new FlatFileItemReaderBuilder<Constructor>()
                .name("constructorItemReader")
                .resource(new ClassPathResource("data/constructors.csv"))
                .delimited()
                .names(new String[]{"constructorId", "constructorRef", "name", "nationality", "url"})
                .linesToSkip(1)
                .fieldSetMapper(new FieldSetMapper<Constructor>() {
                    @Override
                    public Constructor mapFieldSet(FieldSet fs) throws BindException {
                        Constructor c = new Constructor();
                        c.setConstructorId(parseField(fs, "constructorId", Long.class));
                        c.setConstructorRef(parseField(fs, "constructorRef", String.class));
                        c.setName(parseField(fs, "name", String.class));
                        c.setNationality(parseField(fs, "nationality", String.class));
                        c.setUrl(parseField(fs, "url", String.class));
                        return c;
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemWriter<Constructor> constructorWriter() {
        return new RepositoryItemWriterBuilder<Constructor>()
                .repository(constructorRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step loadConstructorsStep(FlatFileItemReader<Constructor> constructorReader,
                                     RepositoryItemWriter<Constructor> constructorWriter) {
        return new StepBuilder("loadConstructorsStep", jobRepository)
                .<Constructor, Constructor>chunk(10, transactionManager)
                .reader(constructorReader)
                .writer(constructorWriter)
                .build();
    }

    // --- 5. Drivers ---

    @Bean
    public FlatFileItemReader<Driver> driverReader() {
        return new FlatFileItemReaderBuilder<Driver>()
                .name("driverItemReader")
                .resource(new ClassPathResource("data/drivers.csv"))
                .delimited()
                .names(new String[]{"driverId", "driverRef", "number", "code", "forename", "surname", "dob", "nationality", "url"})
                .linesToSkip(1)
                .fieldSetMapper(new FieldSetMapper<Driver>() {
                    @Override
                    public Driver mapFieldSet(FieldSet fieldSet) throws BindException {
                        Driver driver = new Driver();
                        driver.setDriverId(parseField(fieldSet, "driverId", Long.class));
                        driver.setDriverRef(parseField(fieldSet, "driverRef", String.class));
                        driver.setNumber(parseField(fieldSet, "number", Integer.class));
                        driver.setCode(parseField(fieldSet, "code", String.class));
                        driver.setForename(parseField(fieldSet, "forename", String.class));
                        driver.setSurname(parseField(fieldSet, "surname", String.class));
                        driver.setDob(parseField(fieldSet, "dob", LocalDate.class));
                        driver.setNationality(parseField(fieldSet, "nationality", String.class));
                        driver.setUrl(parseField(fieldSet, "url", String.class));
                        return driver;
                    }
                })
                .build();
    }

    @Bean
    public RepositoryItemWriter<Driver> driverWriter() {
        return new RepositoryItemWriterBuilder<Driver>()
                .repository(driverRepository)
                .methodName("save")
                .build();
    }

    @Bean
    public Step loadDriversStep(FlatFileItemReader<Driver> driverReader,
                                RepositoryItemWriter<Driver> driverWriter) {
        return new StepBuilder("loadDriversStep", jobRepository)
                .<Driver, Driver>chunk(10, transactionManager)
                .reader(driverReader)
                .writer(driverWriter)
                .build();
    }

    // --- Job Orchestration ---

    @Bean
    public Job historicalDataLoadJob(
            @Qualifier("loadCircuitsStep") Step loadCircuitsStep,
            @Qualifier("loadSeasonsStep") Step loadSeasonsStep,
            @Qualifier("loadStatusStep") Step loadStatusStep,
            @Qualifier("loadConstructorsStep") Step loadConstructorsStep,
            @Qualifier("loadDriversStep") Step loadDriversStep) {

        return new JobBuilder("historicalDataLoadJob", jobRepository)
                .start(loadStatusStep)
                .next(loadSeasonsStep)
                .next(loadCircuitsStep)
                .next(loadConstructorsStep)
                .next(loadDriversStep)
                .build();
    }
}