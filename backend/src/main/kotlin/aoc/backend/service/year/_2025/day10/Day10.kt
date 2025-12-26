package aoc.backend.service.year._2025.day10

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.file.FileValidation
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component

@Component("day10_2025")
@DayInfo(2025, 10)
class Day10() : Day {
    override fun validate(lines: List<String>): Boolean {
        return FileValidation.validate(lines, Regex("^\\[[.#]+]\\s[(\\d,*)\\s]+\\{[\\d,*]+}$"))
    }

    override fun part1(lines: List<String>): Long {
        return -1L
    }

    override fun part2(lines: List<String>): Long {
        return -1L
    }
}