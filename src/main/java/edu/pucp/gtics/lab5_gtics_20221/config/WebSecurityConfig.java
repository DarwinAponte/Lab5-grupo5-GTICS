package edu.pucp.gtics.lab5_gtics_20221.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/user/signIn")
                .loginProcessingUrl("/processLogin")
                .defaultSuccessUrl("/user/signInRedirect", true);

        http.authorizeRequests()
                .antMatchers("/juegos","/juegos/**").hasAnyAuthority("ADMIN","USER")
                .antMatchers("/plataformas","/plataformas/**").hasAnyAuthority("ADMIN")
                .antMatchers("/distribuidoras","/distribuidoras/**").hasAnyAuthority("ADMIN")
                .anyRequest().permitAll();

        http.logout()
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery("select correo, password, enabled from usuarios where correo = ?")
                .authoritiesByUsernameQuery("select correo, autorizacion from usuarios where correo = ?");
    }
}