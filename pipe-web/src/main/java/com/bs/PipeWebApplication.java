package com.bs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@MapperScan(value = {"com.bs.**.mapper"})
public class PipeWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(PipeWebApplication.class, args);
	}

}
