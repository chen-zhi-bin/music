package com.chen.music.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * RBAC role-base-access-control
 * 需要三张表
 * 用户表 --》 角色 --》权限（对应api的访问）
 * 角色表
 * 权限表
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //所有都放行
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
                .and().csrf().disable();
    }

}
