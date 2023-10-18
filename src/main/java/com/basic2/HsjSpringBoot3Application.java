package com.basic2;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HsjSpringBoot3Application {

	public static void main(String[] args) {
//		SpringApplication.run(HsjSpringBoot3Application.class, args);


		SpringApplication application = new SpringApplication(HsjSpringBoot3Application.class);
		application.setWebApplicationType(WebApplicationType.SERVLET);
		application.run(args);
	}

@Bean
public ModelMapper modelMapper() {
ModelMapper modelMapper = new ModelMapper();
return modelMapper; // @Bean으로 설정하였기때문에
//@Autowired로 인젝션 주입 받을 수 있다.
}

}
