package aoc.backend.service.year._2025.day7

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component

@Component("day7_2025")
@DayInfo(2025, 7)
class Day7(): Day {
    override fun part1(lines: List<String>): Long {
        return calculateAllSplitsFromPosition(lines).toLong()
    }

    override fun part2(lines: List<String>): Long {
        return calculateAmountOfTimelines(lines)
    }

    private fun calculateAllSplitsFromPosition(lines: List<String>): Int {
        val beams = mutableSetOf<Int>()
        var amountOfSplits = 0

        beams.add(lines.first().length / 2)

        lines.forEach { line ->
            line.forEachIndexed { position, symbol ->
                if (symbol == '^') {
                    if (beams.remove(position)) amountOfSplits++
                    beams.apply {
                        add(position - 1)
                        add(position + 1)
                    }
                }
            }
        }

        return amountOfSplits
    }

    private fun calculateAmountOfTimelines(lines: List<String>): Long {
        val map = HashMap<Pair<Int, Int>, Long>()
        map[(0 to lines.first().length / 2)] = 1

        lines.forEachIndexed { y, line ->
            line.forEachIndexed { x, symbol ->
                if (symbol == '^') calculateTimelineForCurrentSymbol(map, line, x, y)
            }
            addNonCollidingTimelinesToCurrentLine(map, line, y)
        }

        return map.filter { it.key.first == lines.size - 2 }.map { it.value }.sum()
    }

    private fun calculateTimelineForCurrentSymbol(
        map: HashMap<Pair<Int, Int>, Long>,
        line: String,
        x: Int,
        y: Int
    ) {
        if ((x - 2 >= 0 && line[x - 2] != '^') || x < 2) {
            calculateLeft(map, x, y)
        }
        if (x + 2 < line.length && line[x + 2] == '^') {
            calculateCenter(map, x, y)
        }
        if ((x + 2 < line.length && line[x + 2] != '^') || x > line.length - 3) {
            calculateRight(map, x, y)
        }
    }

    private fun addNonCollidingTimelinesToCurrentLine(
        map: HashMap<Pair<Int, Int>, Long>,
        line: String,
        y: Int
    ) {
        map.filter { it.key.first == y - 2 }.forEach { (_, x), value ->
            if (line[x] == '.' && map[y to x] == null) {
                map[(y to x)] = value
            }
        }
    }

    private fun calculateLeft(map: HashMap<Pair<Int, Int>, Long>, x: Int, y: Int) {
        val yAbove = y - 2
        val xLeft = x - 1

        map[(y to xLeft)] = (map[(yAbove to xLeft)] ?: 0) + (map[(yAbove to x)] ?: 0)
    }

    private fun calculateCenter(map: HashMap<Pair<Int, Int>, Long>, x: Int, y: Int) {
        val yAbove = y - 2
        val xLeft = x
        val xCenter = x + 1
        val xRight = x + 2

        map[(y to xCenter)] =
            (map[(yAbove to xLeft)] ?: 0) +
                    (map[(yAbove to xCenter)] ?: 0) +
                    (map[(yAbove to xRight)] ?: 0)
    }

    private fun calculateRight(map: HashMap<Pair<Int, Int>, Long>, x: Int, y: Int) {
        val yAbove = y - 2
        val xRight = x + 1

        map[(y to xRight)] = (map[(yAbove to x)] ?: 0) + (map[(yAbove to xRight)] ?: 0)
    }
}
