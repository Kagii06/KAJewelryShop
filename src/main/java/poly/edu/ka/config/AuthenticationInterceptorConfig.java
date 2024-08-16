package poly.edu.ka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import poly.edu.ka.interceptor.AdminAuthenticationInterceptor;
import poly.edu.ka.interceptor.SiteAuthenticationInterceptor;

@Configuration
public class AuthenticationInterceptorConfig implements WebMvcConfigurer{
	@Autowired
	private AdminAuthenticationInterceptor adminAuthenticationInterceptor;
	
//	@Autowired
//	private SiteAuthenticationInterceptor siteAuthenticationInterceptor;
//	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(adminAuthenticationInterceptor).addPathPatterns("/admin/**");
//		registry.addInterceptor(siteAuthenticationInterceptor).addPathPatterns("/site/**");
	}
	
	
	
}
