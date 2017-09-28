package ar.com.utn.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by julis on 17/5/2017.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity( securedEnabled = true )
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                    .authorizeRequests()
                        .antMatchers("/publicacion/**").authenticated()
                        .antMatchers("/", "/demo/*","/signup/**", "/publicaciones/**")
                        .permitAll()
                        .anyRequest().authenticated()
                        .and()
                    .formLogin()
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(loginAuthenticationSuccessHandler)
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                        .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .permitAll()
                .and()
                    .exceptionHandling().accessDeniedPage("/index");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
