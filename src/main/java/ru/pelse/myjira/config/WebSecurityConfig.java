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

    /*
        Как много дел неначатых и ждущих,
        Как много стран непознанных, зовущих!
        Как много жизни, что кипит внутри!
        Как много сделано и много впереди.

        Сегодня замечательная дата.
        Твое рождение пусть празднуют вином!
        А я разбавлю день эпистолярным жанром,
        Как осень разбавляет свои дни теплом.
        Мой милый друг, в ком есть рациональность,
        Чтоб ни задумал ты "изобрести",
        Удачное стеченье обстоятельств
        Подспорьем будет на твоем пути.
        Мой "милый друг", - но не словами Мопассана, -
        Я рада, что могу тебя назвать.
        Мне знать тебя приятно и отрадно,
        Что ты из тех, кто может созидать.
        Средь миллиардов жителей планеты
        Мне нравится, что ты из тех людей,
        Кто ценит свои время, труд* и дело**,
        Кому взамен не надо мелочей.
        Такому человеку, если нужно,
        Не откажу подставить хрупкое плечо,
        О чем-то рассказать или послушать,
        Чтоб стало и спокойно, и легко.
        Приятно пожелать тебе успехов,
        Соратников достойных и побед!
        Полезных и рентабельных проектов,
        И в каждом, несомненно, преуспеть!
        Ты не нуждаешься в навязчивых советах.
        Я, к слову, не привыкла их давать.
        А пожелание окончю этим
        Напоминанием о том, что можешь знать:
        "Мой милый друг, мой дорогой Евгений,
        Не отступай от принятых решений.
        И, несмотря на все ограниченья,
        Иди вперед! Пусть медленней, но верно".
        __________________
        * тут результат труда
        ** тут процесс создания полезного

    */

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/webjars/**", "/js/**", "/css/**", "/img/**", "/activate/*");
        // /webjars/** - чтобы JavaScript запускался для всех посетителей, с аутентификацией или без
    }

}
