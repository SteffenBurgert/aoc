package aoc.backend.adapter.controller

import aoc.backend.adapter.dto.AoCSolutionDto
import aoc.backend.adapter.dto.YearsDto
import aoc.backend.service.catalog.DayCatalog
import aoc.backend.service.file.FileConverter
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
class AoCParsingController(
    private val dayCatalog: DayCatalog,
    private val fileConverter: FileConverter,
) {

    @GetMapping("/availability")
    fun availableYearsAndDays(): ResponseEntity<YearsDto> {
        return ResponseEntity.ok(dayCatalog.years)
    }

    @PostMapping(
        "/upload/{year}/{day}",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE]
    )
    fun dayResult(
        @PathVariable year: Int,
        @PathVariable day: Int,
        @RequestParam("file") multipartFile: MultipartFile
    ): ResponseEntity<AoCSolutionDto> {
        val day = dayCatalog.getDayImplementation(year, day)
            ?: return ResponseEntity.notFound().build()

        val lines = fileConverter.convertMultiPartToList(multipartFile)

        if (!day.validate(lines)) return ResponseEntity.badRequest().build()

        return ResponseEntity.ok(AoCSolutionDto(day.part1(lines), day.part2(lines)))
    }
}