package az.ingress;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.springframework.boot.SpringApplication.run;

@EnableFeignClients
@SpringBootApplication
public class MsRecommendationApplication {

    public static void main(String[] args) {
        run(MsRecommendationApplication.class, args);
    }
}