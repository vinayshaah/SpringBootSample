package com.example.wpdelhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * H2 is a sql
 *
 * Relational DB : Oracle, MySQL, Postgresql,MS SQL Server,
 * ACID is used for transaction management
 * Atomicity- Either complete transaction or rollback.
 * Consistency- Read/write must be in sync.After writing, latest data should be read.
 * Isolation-
 * Non Relational DB: Cassandra(Used for billion of inserts), Mongo DB(4.2+ provide ACID),
 * DynamoDB by Amazon(also has ACID properties)
 *
 * Spring MVC:
 * Fron End--->Controller(for resource mapping) -> Service(for business logic) -> Repository(for storing
 * into DB)->DataSource
 * @Service and @Repository are actually alias name for @Componenet
 *
 */
@SpringBootApplication
public class SpringBootSampleApplication {

	public static void main(String[] args) {

		SpringApplication.run(SpringBootSampleApplication.class, args);
		System.out.println("In main");
	}

}
