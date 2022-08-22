package canuran.faster;

import canuran.common.config.ExceptionHandler;
import canuran.common.exception.BusinessException;
import canuran.common.snowflake.SnowflakeIdService;
import canuran.common.utils.Asserts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.function.LongSupplier;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class FasterMain {

    public static final long START_TIME = System.currentTimeMillis();

    public static void main(String[] args) {
        SpringApplication.run(FasterMain.class, args);
    }

    @PostConstruct
    public void globalSettings() {
        Asserts.setDefaultMessager(Asserts.CN_MESSAGER);
        Asserts.setDefaultExceptor(BusinessException::new);
    }

    @Bean
    public ExceptionHandler exceptionHandler() {
        return new ExceptionHandler(FasterMain.class.getPackage().getName());
    }

    @Bean
    public LongSupplier snowflakeSupplier() {
        return new SnowflakeIdService();
    }

}
