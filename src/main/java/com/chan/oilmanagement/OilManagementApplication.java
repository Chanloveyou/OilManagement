package com.chan.oilmanagement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.chan.oilmanagement.dao" )

public class OilManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(OilManagementApplication.class, args);
	}

}
