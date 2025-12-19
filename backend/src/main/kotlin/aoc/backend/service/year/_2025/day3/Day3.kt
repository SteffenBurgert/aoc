package aoc.backend.service.year._2025.day3

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component
import kotlin.math.pow

@Component("day3_2025")
@DayInfo(2025, 3)
class Day3(): Day {

    override fun part1(lines: List<String>): Long {
        var amount = 0L

        lines.forEach { line ->
            val lineList = line.chunked(1).map { it.toInt() }
            amount += calculateJoltage(lineList, 2)
        }

        return amount
    }

    override fun part2(lines: List<String>): Long {
        var amount = 0L

        lines.forEach { line ->
            val lineList = line.chunked(1).map { it.toInt() }
            amount += calculateJoltage(lineList, 12)
        }

        return amount
    }

    private fun calculateJoltage(lineList: List<Int>, resultSize: Int): Long {
        var result = 0L
        var start = 0

        for (i in 1..resultSize) {
            val lastStart = start
            val list = lineList.subList(start, lineList.size - resultSize + i)
            val max = list.max()
            result += (10.0.pow(resultSize - i) * max).toLong()
            start = list.indexOf(max) + lastStart + 1
        }

        return result
    }
}
