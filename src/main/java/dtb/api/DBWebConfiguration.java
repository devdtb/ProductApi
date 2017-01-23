package dtb.api;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DBWebConfiguration {
	public static final String DB_H2_CONSOLE_URL = "/db/h2/console";
	
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new WebServlet());
        registrationBean.addUrlMappings(DB_H2_CONSOLE_URL + "/*");
        return registrationBean;
    }

}

