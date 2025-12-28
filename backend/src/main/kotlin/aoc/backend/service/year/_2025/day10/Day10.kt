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
        val input = getInputParts(lines)

        return -1L
    }

    override fun part2(lines: List<String>): Long {
        return -1L
    }

    private fun getInputParts(lines: List<String>): List<Input> {
        fun String.parseIntList() =
            drop(1).dropLast(1).split(',').map(String::toInt)

        return lines.map { line ->
            line.split(' ').let { parts ->
                Input(
                    signal = parts.first().drop(1).dropLast(1),
                    buttons = parts.drop(1).dropLast(1).map { it.parseIntList() },
                    times = parts.last().parseIntList()
                )
            }
        }
    }
}

data class Input(
    val signal: String,
    val buttons: List<List<Int>>,
    val times: List<Int>,
)
