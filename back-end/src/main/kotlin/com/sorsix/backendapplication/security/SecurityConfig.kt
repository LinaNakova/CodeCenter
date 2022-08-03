package com.sorsix.backendapplication.security

import com.sorsix.backendapplication.domain.enum.AppUserRole
import com.sorsix.backendapplication.security.jwt.AuthTokenFilter
import com.sorsix.backendapplication.service.AppUserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    val authTokenFilter: AuthTokenFilter,
    val appUserService: AppUserService,
    val unauthorizedHandler: AuthEntryPointJwt,
) : WebSecurityConfigurerAdapter() {

    private val publicMatchers = arrayOf(
        "/api/auth/**",
        "/api/questions",
        "/api/questions/withoutAnswers",
        "/api/tags",
        "/api/users",
        "/allQuestionsWithTag/**",
        "/api/questions/sorted",
        "/api/withoutAnswers",
        "/api/allQuestions/**",
        "/api/tag/**",
        "/api/questions/answers/**",
        "/api/questions/tags/**"
    )


    private val adminMatchers = arrayOf(
        "/api/admin/**",
    )

    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests().antMatchers(*publicMatchers).permitAll().and()
            .authorizeRequests().antMatchers(*adminMatchers).hasAuthority(AppUserRole.ADMIN.name)
            .anyRequest().authenticated()

        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(appUserService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }

    @Bean
    fun passwordEncoderBean(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


}

