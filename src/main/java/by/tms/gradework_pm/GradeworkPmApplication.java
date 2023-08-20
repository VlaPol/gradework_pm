package by.tms.gradework_pm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class GradeworkPmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradeworkPmApplication.class, args);
    }

}
