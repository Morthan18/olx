package com.snokant.projekt.Configuration;


import com.snokant.projekt.Configuration.JwtConfiguration.JWTAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class AdapterJWTSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                //.anyRequest().access("hasIpAddress('192.168.0.') and isAuthenticated()")
                .antMatchers("/rest/product/addProduct",
                        "/rest/product/modifyProduct/{id}",
                        "rest/product/myProducts").authenticated()
                //.anyRequest().access("hasIpAddress('0:0:0:0:0:0:0:1') and  isAuthenticated()")
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManagerBean()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().cacheControl();
        http.httpBasic();
    }

}

