package tiendaOnline.Config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "tiendaOnline" })
public class WebMvcConfig implements WebMvcConfigurer  {

	@Autowired
	private ApplicationContext applicationContext;

	/*
	 * Establecemos que los recursos sean cagador de /WEB-INF/templates/ y añadamos
	 * el sufijo .html
	 */
	@Bean
	public SpringResourceTemplateResolver templateResolver() {

		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();

		templateResolver.setApplicationContext(applicationContext);
		templateResolver.setPrefix("/WEB-INF/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setCharacterEncoding("UTF-8");
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		templateResolver.setCacheTTLMs(0L);

		return templateResolver;
	}

	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

	/* Establecemos Thymeleaf como motor de plantillas */
	@Bean
	public SpringTemplateEngine templateEngine() {

		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver());
		templateEngine.setEnableSpringELCompiler(true);
		templateEngine.addDialect(securityDialect());

		return templateEngine;
	}

	/*
	 * Configuramos que las vistas sean resueltaas con el motor de plantillas de
	 * Spring
	 */
	@Bean
	public ViewResolver viewResolver() {

		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine());
		viewResolver.setCharacterEncoding("UTF-8");
		viewResolver.setCache(false);
		return viewResolver;

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	//Configuracion de muulti part para upload file 
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    multipartResolver.setMaxUploadSize(20848820);
	    return multipartResolver;
	}
	
	@Bean
	public StandardServletMultipartResolver multipartResolvers() {
	   return new StandardServletMultipartResolver();
	}
	
	//
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler(
				"/css/**",
				"/js/**",
				"/imagen/**",
				"/i18n/**"
				).addResourceLocations(
						"/WEB-INF/css/",
						"/WEB-INF/js/",
						"/WEB-INF/imagen/",
						"/WEB-INF/i18n/")
				.setCachePeriod(3600);
	}
	
	
	
	     
	 @Bean(name = "localeResolver")
	    public LocaleResolver getLocaleResolver()  {
	        CookieLocaleResolver resolver= new CookieLocaleResolver();
	        resolver.setCookieDomain("myAppLocaleCookie");
	        // 60 minutes 
	        resolver.setCookieMaxAge(60*60); 
	        return resolver;
	    } 
	     
	    @Bean(name = "messageSource")
	    public MessageSource getMessageResource()  {
	        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
	        messageResource.setBasename("classpath:i18n/messages");
	        messageResource.setDefaultEncoding("UTF-8");
	        return messageResource;
	    }
	    @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
	        localeInterceptor.setParamName("lang");
	         
	         
	        registry.addInterceptor(localeInterceptor).addPathPatterns("/*");
	    }
}