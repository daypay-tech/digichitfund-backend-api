package com.daypaytechnologies.digichitfund.security;

import com.daypaytechnologies.digichitfund.security.authprovider.AdministrationUserAuthenticationProvider;
import com.daypaytechnologies.digichitfund.security.authprovider.MemberAuthenticationProvider;
import com.daypaytechnologies.digichitfund.security.filters.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SpringSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    private final AuthenticationEntryPoint unauthorizedHandler;

    private final AuthTokenFilter authenticationJwtTokenFilter;

    private final AdministrationUserAuthenticationProvider adminCustomAuthenticationProvider;

    private final MemberAuthenticationProvider memberAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(memberAuthenticationProvider);
        auth.authenticationProvider(adminCustomAuthenticationProvider);
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //.antMatchers("/api/v1/categories/**").permitAll()
                //.antMatchers("/api/v1/polls/**").permitAll()
                .antMatchers("/api/v1/members/**").permitAll()
                .antMatchers("/api/v1/answers/**").permitAll()
                .antMatchers("/api/v1/questions/**").permitAll()
                .antMatchers("/api/v1/administrations/**").permitAll()
                .antMatchers("/api/v1/ages/**").permitAll()
                .antMatchers("/api/v1/genders/**").permitAll()
                .antMatchers("/api/v1/charts/**").permitAll()
                .antMatchers("/api/v1/testAnswer/**").permitAll()
                .antMatchers("/api/v1/roles/**").permitAll()
                .antMatchers("/api/v1/organizations/**").permitAll()
                .anyRequest()
                .authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
