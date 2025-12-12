package aoc.backend.year._2025.day1

import aoc.backend.year.Day
import org.springframework.stereotype.Service
import java.io.File

@Service
class Day1() : Day {
    override fun part1(file: File): Long {
        var start = START
        var zeroAmount = 0

        file.readLines().forEach { line ->
            val movement = line.drop(1).toInt()

            val (newStart, onZero) = amountOfStoppedZeros(line.first(), start, movement)

            start = newStart
            zeroAmount += onZero
        }

        return zeroAmount.toLong()
    }

    override fun part2(file: File): Long {
        var start = START
        var zeroAmount = 0

        file.readLines().forEach { line ->
            val movement = line.drop(1).toInt()
            val direction = line.first()

            val (newStart, onZero) = amountOfStoppedZeros(direction, start, movement)
            val rotatedZeros = amountOfZerosOnRotation(direction, start, movement)

            zeroAmount += onZero + rotatedZeros
            start = newStart
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

        zeroOnRotation += when (direction) {
            'L' -> if (startPosition != 0 && startPosition - movement < 0) 1 else 0
            'R' -> if (startPosition + movement > 100) 1 else 0
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
