package aoc.backend.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title("Swagger AoC Backend - OpenAPI 3.0")
                .version("0.0.1")
                .description(
                    """
                    # AoC Backend
                    
                    ## Technologies
                    
                    - Spring Boot: Framework for building Kotlin/Java applications.
                    - Gradle: Build automation tool for Kotlin/Java projects.
                    - Kotlin: Statically-typed language, interoperable with Java.
                    """.trimIndent()
                )
        )
    }
}