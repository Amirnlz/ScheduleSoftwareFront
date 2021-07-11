package com.finalproject.schedule.Configures;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@EnableAutoConfiguration
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    private final DataSource dataSource;

    public SpringSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select code,password,enabled from user_tbl where code=?")
                .authoritiesByUsernameQuery("select code,roles from authorities where code=?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers( "/loginrole", "/style.css", "/index.js", "/css/**", "/js/**", "/assets/**", "/fontawesome-free/**")
                .permitAll()


                .antMatchers("/Users/**", "/Courses/**", "/Days/**", "/Bells/**", "/timetabelbell/**", "/admin_main",
                        "/api/Users/**", "/api/Courses/**", "/api/Days/**", "/api/Bells/**", "/api/timetabelbell/**","/admin_timetable","/api/**")
                .hasAuthority("ADMIN")


                .antMatchers("/master_main/**", "/master_course/**", "/master_timetable/**",
                        "/api/MasterCourses/**","/api/Announcements/**")
                .hasAuthority("MASTER")


                .antMatchers("/student_main/**","/student_choose/**","/api/TimeTableChoose/**")
                .hasAuthority("STUDENT")


                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login").usernameParameter("code")
                .permitAll().and().logout().logoutSuccessUrl("/").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration=new CorsConfiguration();
        List<String>origins= Arrays.asList(new String[]{"*"});
        configuration.setAllowedOrigins(origins);
        configuration.setAllowedMethods(Arrays.asList(new String[]{"HEAD","GET","POST","PUT","DELETE","PATCH"}));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList(new String[]{"Authorization", "Cache-Control", "Content-Type"}));
        final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return  source;
    }


}
