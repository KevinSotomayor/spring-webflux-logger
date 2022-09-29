package com.example.benefits.Logger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@SpringBootApplication(exclude = [DataSourceAutoConfiguration::class])
class LoggerApplication

fun main(args: Array<String>) {
	runApplication<LoggerApplication>(*args)
}
