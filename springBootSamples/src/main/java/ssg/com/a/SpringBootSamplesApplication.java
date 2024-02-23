package ssg.com.a;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class SpringBootSamplesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSamplesApplication.class, args);
	}

}
