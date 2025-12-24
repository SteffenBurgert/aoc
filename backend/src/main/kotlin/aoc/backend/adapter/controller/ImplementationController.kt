package aoc.backend.adapter.controller

import aoc.backend.model.Language
import aoc.backend.service.file.SourceFileExtractor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/implementation")
class ImplementationController(
    private val sourceFileExtractor: SourceFileExtractor,
) {

    @GetMapping("/{language}/{year}/{day}")
    fun getImplementation(@PathVariable language: Language, @PathVariable year: Int, @PathVariable day: Int): ResponseEntity<String> {
        return try {
            when (language) {
                Language.KOTLIN -> {
                    ResponseEntity.ok(sourceFileExtractor.getResourceFileAsString("sourceFiles/kotlin/_$year/day$day/Day$day.kt"))
                }
                Language.GO -> {
                    return ResponseEntity.ok(sourceFileExtractor.getResourceFileAsString("sourceFiles/go/_$year/day$day.go"))
                }
            }
        } catch (_: IllegalArgumentException) {
            ResponseEntity.ok("No $language Implementation for this day yet.")
        }
    }
}