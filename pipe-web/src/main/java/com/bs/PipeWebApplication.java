package com.bs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(value = {"com.bs.**.mapper"})
public class PipeWebApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(PipeWebApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(PipeWebApplication.class);
    }

}
