package com.snokant.projekt.Configuration;

//import com.snokant.projekt.Configuration.JwtConfiguration.JWTAuthenticationFilter;
import com.snokant.projekt.Configuration.JwtConfiguration.JWTAuthenticationFilter;
import com.snokant.projekt.Configuration.JwtConfiguration.JWTAuthorizationFilter;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtAuthenticationEntryPoint;
import com.snokant.projekt.Configuration.JwtConfiguration.JwtSuccessHandler;
import com.snokant.projekt.Service.UserServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.Collections;

@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class AdapterJWTSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;




    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private UserServiceImpl userDetailsService;

    public AdapterJWTSecurityConfig(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth){
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/rest/user/signin").permitAll()
                .anyRequest().hasIpAddress("192.168.0.8")
                .anyRequest().hasIpAddress("127.0.0.1")
                .anyRequest().permitAll()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManagerBean()))
                .addFilter(new JWTAuthorizationFilter(authenticationManagerBean()))
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}