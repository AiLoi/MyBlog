package com.blog.config;

import com.blog.security.QQAuthenticationFilter;
import com.blog.security.QQAuthenticationManager;
import com.blog.security.SecurityUserDetailsService;
import com.blog.security.handler.*;
import com.blog.service.BlogUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @program: MyBlog
 * @description: Spring Security 核心配置
 * @author: Ailuoli
 * @create: 2019-05-02 13:50
 **/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${system.user.password.secret}")
    private String secret;

    @Value("${security.remember.time}")
    private Integer rememberTime;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private BlogUserService blogUserService;

    @Autowired
    private DataSource dataSource;

    /*
    自定义登录成功处理器
     */
    @Autowired
    private FuryAuthSuccessHandler furyAuthSuccessHandler;

    /**
     * 自定义登录失败处理器
     */
    @Autowired
    private FuryAuthFailureHandler furyAuthFailureHandler;

    /**
     * 自定义的注销成功的处理器
     */
    @Autowired
    private FuryLogoutSuccessHandler logoutSuccessHandler;


    @Autowired
    private FuryAuthenticationEntryPoint authenticationEntryPoint;

    /**
     * 自定义注册没有权限的处理器
     */
    @Autowired
    private RestAuthAccessDeniedHandler restAuthAccessDeniedHandler;


    /*
    配置TokenRepository
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }


    //配置全局加密
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new Pbkdf2PasswordEncoder(secret));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        //设置登录页面
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/write/get_index_article", "/index/preview/get_right_preview", "/blog/user/registered", "/image/**", "/blog/user/is_expired").permitAll()
                .antMatchers("/403", "/404", "/user/login/**", "/auth/login", "/session/invalid", "/auth/logout", "/user/registered/**", "/index", "/","/qqlogin/**").permitAll()
                .antMatchers("/js/**", "/css/**", "/img/**", "/fonts/**", "/icon/**", "/layer/**", "/markdown/**", "/layui/**", "/jedate-6.5.0/**", "/md/**").permitAll()
                .anyRequest()
                .authenticated()
                .anyRequest()
                .access("@securityAuthorityDecision.hasPermission(request,authentication)")
                .and()
                .addFilterAt(qqAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/user/login")
                .loginProcessingUrl("/auth/login")
                .successHandler(furyAuthSuccessHandler)
                .failureHandler(furyAuthFailureHandler)
                .and().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .logout().logoutUrl("/auth/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(restAuthAccessDeniedHandler)
                .and()
                .rememberMe()//记住我相关配置
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(rememberTime); //两个星期

        //设置session过期处理器
        http.sessionManagement()
                .invalidSessionUrl("/session/invalid");
        //响应头配置
        http.headers().frameOptions().sameOrigin();
    }


    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new Pbkdf2PasswordEncoder(secret);
    }


    /**
     * 自定义 QQ登录 过滤器
     */
    private QQAuthenticationFilter qqAuthenticationFilter() {

        QQAuthenticationFilter authenticationFilter = new QQAuthenticationFilter("/qqlogin/success");
//        SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
//        simpleUrlAuthenticationSuccessHandler.setAlwaysUseDefaultTargetUrl(true);
//
////        simpleUrlAuthenticationSuccessHandler.setDefaultTargetUrl("/qqlogin/success");
//
//        authenticationFilter.setAuthenticationSuccessHandler(simpleUrlAuthenticationSuccessHandler);
//
//        authenticationFilter.setAuthenticationSuccessHandler(furyAuthSuccessHandler);

        authenticationFilter.setAuthenticationSuccessHandler(furyAuthSuccessHandler);

        authenticationFilter.setAuthenticationManager(new QQAuthenticationManager(blogUserService));
        return authenticationFilter;
    }
}

