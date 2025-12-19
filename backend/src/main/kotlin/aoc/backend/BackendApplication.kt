package aoc.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan("aoc.backend")
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
