//package ru.belogurow.socialnetworkserver.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private DataSource dataSource;
//
//

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws CustomException {
//        super.configure(auth);
//
//        auth.jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery("SELECT username, password FROM user_account WHERE username = ?")
//                .authoritiesByUsernameQuery("SELECT username, user_status FROM user_account WHERE username = ?")
//                .passwordEncoder(encoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws CustomException {
//        http.authorizeRequests().anyRequest()
//                .hasAnyRole(
//                        UserRole.ROLE_ADMIN.toString(),
//                        UserRole.ROLE_USER.toString())
//                .and()
//                .authorizeRequests().antMatchers("/loginDeprecated**").permitAll()
//                .and()
//                .formLogin().loginPage("/loginDeprecated").loginProcessingUrl("/loginAction").permitAll()
//                .and()
//                .logout().logoutSuccessUrl("/loginDeprecated").permitAll()
//                .and()
//                .csrf().disable();
//    }
//}
