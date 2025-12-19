package aoc.backend.service.year._2025.day1

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component

@Component("day1_2025")
@DayInfo(2025, 1)
class Day1() : Day {
    override fun part1(lines: List<String>): Long {
        var start = START
        var zeroAmount = 0

        lines.forEach { line ->
            val movement = line.drop(1).toInt()

            val (newStart, onZero) = amountOfStoppedZeros(line.first(), start, movement)

            start = newStart
            zeroAmount += onZero
        }

        return zeroAmount.toLong()
    }

    override fun part2(lines: List<String>): Long {
        var start = START
        var zeroAmount = 0

        lines.forEach { line ->
            val movement = line.drop(1).toInt()
            val direction = line.first()

            val (newStart, onZero) = amountOfStoppedZeros(direction, start, movement)
            val rotatedZeros = amountOfZerosOnRotation(direction, start, movement)

            start = newStart
            zeroAmount += (onZero + rotatedZeros)
        }

        return zeroAmount.toLong()
    }

    private fun amountOfStoppedZeros(
        direction: Char,
        startPosition: Int,
        movement: Int
    ): Pair<Int, Int> {
        var start = startPosition

        start = when (direction) {
            'L' -> Math.floorMod(start - movement, 100)
            'R' -> (start + movement).mod(100)
            else -> throw IllegalArgumentException(errorMessage(direction))
        }

        return if (start == 0) Pair(start, 1) else Pair(start, 0)
    }

    private fun amountOfZerosOnRotation(
        direction: Char,
        startPosition: Int,
        movement: Int
    ): Int {
        var zeroOnRotation = movement / 100
        val remainingMovement = movement - 100 * zeroOnRotation

        zeroOnRotation += when (direction) {
            'L' -> if (startPosition != 0 && startPosition - remainingMovement < 0) 1 else 0
            'R' -> if (startPosition + remainingMovement > 100) 1 else 0
            else -> throw IllegalArgumentException(errorMessage(direction))
        }

        return zeroOnRotation
    }

    companion object {
        private const val START = 50
        private fun errorMessage(direction: Char) =
            "Direction value ($direction) not valid. Should be 'L' or 'R'!"
    }
}
