package com.ep.challenge

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration
import org.springframework.boot.runApplication

@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@SpringBootApplication
class DuckCodeChallengeApplication

fun main(args: Array<String>) {
	runApplication<DuckCodeChallengeApplication>(*args)
}
