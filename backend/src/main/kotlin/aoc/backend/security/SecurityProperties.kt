package aoc.backend.security

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties("aoc.configuration")
data class SecurityProperties(
    var enableApi: Boolean = true,
    var enableSwagger: Boolean = false,
)
