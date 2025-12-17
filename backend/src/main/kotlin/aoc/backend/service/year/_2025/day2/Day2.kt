package aoc.backend.service.year._2025.day2

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component

@Component("day2_2025")
@DayInfo(2025, 2)
class Day2(): Day {
    override fun part1(lines: List<String>): Long {
        var invalidIdSum = 0L

        lines.forEach { line ->
            line.split(",").forEach { range ->
                val (start, end) = range.split('-').map { it.toLong() }

                for (value in start..end) {
                    invalidIdSum += part1(value)
                }
            }
        }

        return invalidIdSum
    }

    override fun part2(lines: List<String>): Long {
        var invalidIdSum = 0L

        lines.forEach { line ->
            line.split(",").forEach { range ->
                val (start, end) = range.split('-').map { it.toLong() }

                for (value in start..end) {
                    invalidIdSum += part2(value)
                }
            }
        }

        return invalidIdSum
    }

    private fun part1(value: Long): Long {
        val valueAsString = value.toString()

        if (valueAsString.length % 2 == 0) {
            valueAsString.splitIntInParts().let { (left, right) ->
                if (left == right) return value
            }
        }

        return 0
    }

    private fun part2(value: Long): Long {
        val valueAsString = value.toString()

        for (splits in 1 .. valueAsString.length / 2) {
            if (valueAsString.length % splits == 0) {
                valueAsString.splitIntInParts(splits).let {
                    if (it.all { value -> value == it.first() }) {
                        return value
                    }
                }
            }
        }

        return 0
    }

    private fun String.splitIntInParts(): Pair<Long, Long> {
        val mid = this.length / 2
        return Pair(this.take(mid).toLong(), this.substring(mid).toLong())
    }

    private fun String.splitIntInParts(partSize: Int): List<Long> {
        val list = mutableListOf<Long>()
        var currentPos = 0
        repeat(this.length / partSize) {
            list.add(this.substring(currentPos, currentPos + partSize).toLong())
            currentPos += partSize
        }
        return list
    }
}
