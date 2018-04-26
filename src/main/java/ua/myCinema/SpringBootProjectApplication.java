package ua.myCinema;

import java.util.Calendar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import ua.myCinema.entity.UserEntity;
import ua.myCinema.entity.enumeration.Role;
import ua.myCinema.repository.UserRepository;

@SpringBootApplication
public class SpringBootProjectApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(SpringBootProjectApplication.class);
	}

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringBootProjectApplication.class, args);
		addAdmin(context);
	}
	
	static void addAdmin(ConfigurableApplicationContext context) {
		String adminEmail = "misha89admin@gmail.com";
		String adminPassword = "2424";
		String adminFirstName = "Admin";
		
		UserRepository userRepository = context.getBean(UserRepository.class);
		UserEntity entity = userRepository.findUserByEmail(adminEmail);
		if(entity == null) {
			PasswordEncoder encoder = context.getBean(PasswordEncoder.class);
			
			entity = new UserEntity();
			entity.setEmail(adminEmail);
			entity.setPassword(encoder.encode(adminPassword));
			entity.setFirstName(adminFirstName);
			entity.setDateOfBirdth(Calendar.getInstance());
			entity.setRole(Role.ROLE_ADMIN);
			
			userRepository.save(entity);
		}
	}
	
}
