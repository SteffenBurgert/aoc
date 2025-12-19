package aoc.backend.service.year._2025.day5

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.file.FileValidation
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component

@Component("day5_2025")
@DayInfo(2025, 5)
class Day5() : Day {
    override fun validate(lines: List<String>): Boolean {
        val firstPart = lines.filter { it.contains("-") && it.isNotBlank() }
        val secondPart = lines.filter { !it.contains("-") && it.isNotBlank() }

        return FileValidation.validate(firstPart, Regex("[0-9]+-[0-9]+"))
                && FileValidation.validate(secondPart, Regex("[0-9]+"))
                && firstPart.isNotEmpty() && secondPart.isNotEmpty()
    }

    override fun part1(lines: List<String>): Long {
        val (ranges, ids) = getRangesAndIdsFromLines(lines)

        var freshIds = 0L
        ids.forEach { id ->
            if (ranges.any { it.contains(id) }) {
                freshIds++
            }
        }

        return freshIds
    }

    override fun part2(lines: List<String>): Long {
        val (ranges, _) = getRangesAndIdsFromLines(lines)

        ranges.sortBy { it.first }

        var min = ranges.first().first

        var amount = 0L
        ranges.forEach { range ->
            if (range.last < min) return@forEach

            val start = if (range.first > min) range.first else min
            amount += range.last - start + 1

            min = range.last + 1
        }

        return amount
    }

    private fun getRangesAndIdsFromLines(lines: List<String>): Pair<MutableList<LongRange>, List<Long>> {
        val ranges = mutableListOf<LongRange>()
        val ids = mutableListOf<Long>()

        lines.forEach { line ->
            if (line.contains('-')) {
                val (left, right) = line.split("-").map { it.toLong() }
                ranges.add(LongRange(left, right))
            } else if (line.isNotEmpty()) {
                ids.add(line.toLong())
            }
        }

        return Pair(ranges, ids)
    }
}
