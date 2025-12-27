package aoc.backend.adapter.controller

import aoc.backend.adapter.dto.ImplementationResultDto
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
    fun getImplementation(@PathVariable language: Language, @PathVariable year: Int, @PathVariable day: Int): ResponseEntity<ImplementationResultDto> {
        return try {
            when (language) {
                Language.KOTLIN -> {
                    ResponseEntity.ok(ImplementationResultDto(true,sourceFileExtractor.getResourceFileAsString("sourceFiles/kotlin/_$year/day$day/Day$day.kt")))
                }
                Language.GO -> {
                    return ResponseEntity.ok(ImplementationResultDto(true,sourceFileExtractor.getResourceFileAsString("sourceFiles/go/_$year/day$day.go")))
                }
            }
        } catch (_: IllegalArgumentException) {
            ResponseEntity.ok(ImplementationResultDto(false,"No $language implementation for this day yet."))
        }
    }
}