package life.pxx.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author pxx
 * Date 2019/9/29 8:54
 * @Description
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	private SectionInterception sectionInterception;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	
		registry.addInterceptor(sectionInterception).addPathPatterns("/**");
	}
}
