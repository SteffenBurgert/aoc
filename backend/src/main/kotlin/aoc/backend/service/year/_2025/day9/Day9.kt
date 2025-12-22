package aoc.backend.service.year._2025.day9

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.file.FileValidation
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component
import kotlin.math.abs

@Component("day9_2025")
@DayInfo(2025, 9)
class Day9() : Day {
    override fun validate(lines: List<String>): Boolean {
        return FileValidation.validate(lines, Regex("\\d+,\\d+"))
    }

    override fun part1(lines: List<String>): Long {
        val coordinates = getCoordinates(lines)
        val biggestArea = findBiggestArea(coordinates)

        return biggestArea.volume
    }

    override fun part2(lines: List<String>): Long {
        //TODO("Not yet implemented")
        return -1L
    }

    private fun getCoordinates(lines: List<String>): List<Coordinate> {
        return lines.map { line ->
            line.split(",").map(String::toInt)
                .run { Coordinate(this[0], this[1]) }
        }
    }

    private fun findBiggestArea(coordinates: List<Coordinate>): Area {
        var biggestArea: Area? = null
        coordinates.forEachIndexed { index, coordinate ->
            coordinates.drop(index + 1).forEach { nextCoordinate ->
                val volume: Long = calculateVolume(coordinate, nextCoordinate)
                if ((biggestArea?.volume ?: Long.MIN_VALUE) < volume) {
                    biggestArea = Area(coordinate, nextCoordinate, volume)
                }
            }
        }

        if (biggestArea == null) throw NullPointerException("biggest Area is null")

        return biggestArea
    }

    private fun calculateVolume(first: Coordinate, second: Coordinate): Long {
        return abs(first.x - second.x + 1L) * abs(first.y - second.y + 1L)
    }
}

data class Coordinate(
    val x: Int,
    val y: Int,
)

data class Area(
    val first: Coordinate,
    val second: Coordinate,
    val volume: Long
)