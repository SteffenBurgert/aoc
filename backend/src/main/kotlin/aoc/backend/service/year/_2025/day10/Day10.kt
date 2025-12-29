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
        val presses = input.sumOf { getPossibleCombinationsSorted(it).first().presses }

        return presses.toLong()
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
                    signal = parts.first().drop(1).dropLast(1).withIndex()
                        .filter { it.value == '#' }
                        .sumOf { 1 shl it.index },
                    buttons = parts.drop(1).dropLast(1)
                        .map { it.parseIntList() }
                        .map { it.sumOf { value -> 1 shl value } },
                    buttonsList = parts.drop(1).dropLast(1).map { it.parseIntList() },
                    times = parts.last().parseIntList()
                )
            }
        }
    }

    private fun getPossibleCombinationsSorted(input: Input): List<Combination> {
        val combinations = mutableListOf<Combination>()

        for (round in 1 until (1 shl input.buttons.size)) {
            val positions = getOnePositions(round)
            val signalResult = positions.fold(0) { acc, pos -> acc xor input.buttons[pos] }

            if (signalResult == input.signal) {
                combinations.add(Combination(positions, positions.size))
            }
        }

        return combinations.sortedBy { it.presses }
    }

    private fun getOnePositions(number: Int): List<Int> {
        val positions = mutableListOf<Int>()
        var n = number
        var position = 0

        while (n != 0) {
            if (n and 1 == 1) {
                positions.add(position)
            }
            n = n shr 1
            position++
        }

        return positions
    }
}

data class Input(
    val signal: Int,
    val buttons: List<Int>,
    val buttonsList: List<List<Int>>,
    val times: List<Int>,
)

data class Combination(
    val buttonCombinations: List<Int>,
    val presses: Int,
)
