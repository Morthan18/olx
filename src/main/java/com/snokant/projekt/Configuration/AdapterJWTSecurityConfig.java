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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.sql.DataSource;
import java.util.Collections;
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
//@Configuration
public class AdapterJWTSecurityConfig {

    @Configuration
    public static class config extends WebSecurityConfigurerAdapter{
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth){
//        }
//        @Bean
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
        protected void configure(HttpSecurity http) throws Exception {
            http
                   // .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                   // .and()
                   // .csrf().disable()
                    //.httpBasic().disable()
                   // .cors()
                   // .and()
                    //.anonymous().and()
                    .authorizeRequests().antMatchers(HttpMethod.POST).permitAll()
                    .anyRequest().permitAll();
                   // .and()


                    //.addFilter(new JWTAuthorizationFilter(authenticationManagerBean()));

//               // .addFilter(new JWTAuthenticationFilter(authenticationManagerBean()))
////                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)

//
        }

    }

//    @Order(1)
//    @Configuration
//    public static class config2 extends WebSecurityConfigurerAdapter{
//        protected void configure(HttpSecurity http) throws Exception {
//
//
//        }
//
//    }
    @Autowired
    DataSource dataSource;
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;











}