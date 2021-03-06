package ru.pelse.myjira.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import ru.pelse.myjira.service.UserService;

@Configuration
@EnableWebSecurity //Аннотация @EnableWebSecurity в связке с WebSecurityConfigurerAdapter
// классом работает над обеспечением аутентификации
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder getPasswordEncoder()
    {
        return new BCryptPasswordEncoder(8);//параметр ключа шифрования
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/registration", "/static/**", "/activate/*", "/error").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )     // - это HttpStatus - ответ 401
                .oauth2Login()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile").failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/webjars/**", "/js/**", "/css/**", "/img/**", "/activate/*");
        // /webjars/** - чтобы JavaScript запускался для всех посетителей, с аутентификацией или без
    }

}
