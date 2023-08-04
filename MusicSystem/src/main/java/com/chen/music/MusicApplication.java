package com.chen.music;

import com.chen.music.utils.IdWorker;
import com.chen.music.utils.RedisUtils;
import com.google.gson.Gson;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Random;

@MapperScan("com.chen.music.mapper")
@EnableSwagger2
@SpringBootApplication
public class MusicApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicApplication.class, args);
    }
    @Bean
    public IdWorker createIdWorker(){
        return new IdWorker(0,0);
    }

    @Bean
    public BCryptPasswordEncoder createPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RedisUtils createRedisUtils(){
        return new RedisUtils();
    }

    @Bean
    public Random createRadom(){
        return new Random();
    }

    @Bean
    public Gson createGson(){
        return new Gson();
    }
}
