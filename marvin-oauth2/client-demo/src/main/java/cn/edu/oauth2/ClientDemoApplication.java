package cn.edu.oauth2;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(ClientDemoApplication.class);
		application.setBannerMode(Banner.Mode.CONSOLE);
		SpringApplication.run(ClientDemoApplication.class, args);
	}

}

