package aoc.backend.service.catalog

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DayInfo(
    val year: Int,
    val day: Int
)
