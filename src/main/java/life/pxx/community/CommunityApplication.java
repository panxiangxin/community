package life.pxx.community;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author www72
 */
@SpringBootApplication
@EnableScheduling
@MapperScan(basePackages = "life.pxx.community.mapper")
public class CommunityApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CommunityApplication.class, args);
	}
	
}
