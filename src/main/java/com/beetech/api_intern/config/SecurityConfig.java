package com.beetech.api_intern.config;

import com.beetech.api_intern.common.utils.CustomPasswordEncoder;
import com.beetech.api_intern.features.role.RoleEnum;
import com.beetech.api_intern.features.user.UserService;
import com.beetech.api_intern.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The type Security config.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    /**
     * inject user service bean
     */
    private final UserService userService;
    /**
     * inject custom password encoder bean
     */
    private final CustomPasswordEncoder passwordEncoder;
    /**
     * inject jwt authentication filter bean
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Auth manager authentication manager.
     *
     * @param http               the http
     * @param userDetailsService the user details service
     * @return the authentication manager
     * @throws Exception the exception
     */
    @Bean
    public AuthenticationManager authManager(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(this.passwordEncoder).and().build();
    }

    /**
     * Authentication provider dao authentication provider.
     *
     * @return the dao authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(this.userService);
        auth.setPasswordEncoder(this.passwordEncoder);
        return auth;
    }

    /**
     * Filter chain security filter chain.
     *
     * @param http the http
     * @return the security filter chain
     * @throws Exception the exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults());
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.ignoringRequestMatchers("/api/**"));

        http.authorizeHttpRequests(requestMatcherRegistry -> {
            requestMatcherRegistry.requestMatchers("/api/request-password").permitAll();
            requestMatcherRegistry.requestMatchers("/api/reset-password").permitAll();
            requestMatcherRegistry.requestMatchers("/api/users").hasAuthority(RoleEnum.ADMIN.toString());
        });

        http.authorizeHttpRequests()
                .requestMatchers("/api/auth/**")
                .permitAll()
                .and();

        http.authorizeHttpRequests()
                .requestMatchers("/api/**")
                .authenticated().and();

        http.authorizeHttpRequests()
                .requestMatchers("/**")
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
