package com.example.datnsd56.security;

import com.example.datnsd56.repository.AccountRepository;
//import com.example.datnsd56.service.impl.UserInforDetailService;
//import com.example.datnsd56.service.impl.UserInforDetailService;
//import com.example.datnsd56.service.impl.UserInforDetailService;
//import com.example.datnsd56.security1.UserService;
//import com.example.datnsd56.service.impl.UserInforDetailService;
//import com.example.datnsd56.service.impl.UserInforDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
@RequiredArgsConstructor
public class Config {
    //sql
    @Autowired
    private final AccountRepository repository;
//@Autowired
//    UserService userService;
    //sql- start
    @Bean
    public UserDetailsService userDetailsService(  ) {
        return new UserInforDetailService(repository);
        };
//    }
//    sql- end
//    @Bean
//    protected void configure(AuthenticationManagerBuilder auth)
//            throws Exception {
//        auth.userDetailsService(userService) // Cung cáp userservice cho spring security
//            .passwordEncoder(passwordEncoder()); // cung cấp password encoder
//    }
//    fix cung - start
//@Bean
//public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//    UserDetails admin = User.withUsername("cam")
//            .password(encoder.encode("cam"))
//            .roles("ADMIN")
//            .build();
//    UserDetails user = User.withUsername("tao")
//            .password(encoder.encode("tao"))
//            .roles("USER")
//            .build();
//    return new InMemoryUserDetailsManager(admin, user);
//}
//fix cung end
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/hello","/admin/account/hien-thi").permitAll() // với endpoint /hello thì sẽ được cho qua
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers("/customer/**,").authenticated() // với endpoint /customer/** sẽ yêu cầu authenticate
//                .and().formLogin() // trả về page login nếu chưa authenticate
//                .and().build();
//    }
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers("/dashboard/css/**", "/dashboard/js/**", "/dashboard/img/**","/dashboard/bundles/**","/dashboard/fonts/**","/dashboard/lib/**","/dashboard/scss/**").permitAll()
            .requestMatchers("/website/css/**", "/website/js/**", "/website/img/**", "/website/lib/**", "/website/scss/**").permitAll()
            .requestMatchers("/hello").permitAll()
            .requestMatchers("/product/**").permitAll()// với endpoint /hello thì sẽ được cho qua
            .and()
            .authorizeHttpRequests()
            .requestMatchers("/customer/**").authenticated() // với endpoint /customer/** sẽ yêu cầu authenticate
            .requestMatchers("/admin/**").authenticated() // với endpoint /customer/** sẽ yêu cầu authenticate
            .and().formLogin()// trả về page login nếu chưa authenticate
           .defaultSuccessUrl("/admin/thuong-hieu/hien-thi")

//            .loginPage("/login/custom-login").permitAll()//.successHandler(new SimpleUrlAuthenticationSuccessHandler()).failureHandler(new SimpleUrlAuthenticationFailureHandler())
//            .and()
//            .logout().permitAll()
            .and().build();
}
//@Bean
//public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http.csrf().disable()
//            .authorizeHttpRequests()
//            .requestMatchers("/dashboard/css/**", "/dashboard/js/**", "/dashboard/img/**","/dashboard/bundles/**","/dashboard/fonts/**","/dashboard/lib/**","/dashboard/scss/**").permitAll()
//            .requestMatchers("/website/css/**", "/website/js/**", "/website/img/**", "/website/lib/**", "/website/scss/**").permitAll()
//            .requestMatchers("/hello").permitAll()
//            .requestMatchers("/product/**").permitAll()// với endpoint /hello thì sẽ được cho qua
//            .and()
//            .authorizeHttpRequests()
//            .requestMatchers("/customer/**").authenticated() // với endpoint /customer/** sẽ yêu cầu authenticate
//            .requestMatchers("/admin/**").authenticated() // với endpoint /customer/** sẽ yêu cầu authenticate
//            .and()
//            .formLogin()
//            .loginPage("/login/custom-login") // Specify the custom login page URL
//            .loginProcessingUrl("/login") // Specify the login processing URL
//            .defaultSuccessUrl("/admin/thuong-hieu/hien-thi")
//            .and()
//            .build();
//}


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http.csrf().disable()
//                .authorizeHttpRequests()
//                .antMatchers("/hello").permitAll()
//                .antMatchers("/admin/account/hien-thi").permitAll()
//                .antMatchers("/customer/**").authenticated()
//                .antMatchers("/admin/**").authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/admin/account/custom-login") // Uncomment this line if you have a custom login page
//                .defaultSuccessUrl("/admin") // Redirect to /admin after successful login
//                .and()
//                .build();
//    }


//sql
@Bean
public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
}


}
