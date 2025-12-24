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
        val coordinates = getCoordinates(lines)
        val areas = calculateAreasSortedByVolume(coordinates)

        getBordersOfShape(coordinates)

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

    private fun calculateAreasSortedByVolume(coordinates: List<Coordinate>): List<Area> {
        return coordinates.flatMapIndexed { index, coordinate ->
            coordinates.drop(index + 1).map { nextCoordinate ->
                val volume: Long = calculateVolume(coordinate, nextCoordinate)
                Area(coordinate, nextCoordinate, volume)
            }
        }.sortedBy { it.volume }
    }

    private fun calculateVolume(first: Coordinate, second: Coordinate): Long {
        return abs(first.x - second.x + 1L) * abs(first.y - second.y + 1L)
    }

    private fun getBordersOfShape(coordinates: List<Coordinate>) {
        val sortedCoordinates =
            coordinates.sortedWith(compareBy<Coordinate> { it.y }.thenBy { it.x })

        val connections = mutableListOf<Connection>()

        sortedCoordinates.forEach { coordinate ->
            val connectionsToCoordinate = connectionsToCoordinate(connections, coordinate)
            if (connectionsToCoordinate.size == 2) return@forEach

            when (connectionsToCoordinate.size) {
                0 -> {
                    val rightNeighbour = rightNeighbour(sortedCoordinates, coordinate)
                    val belowNeighbour = belowNeighbour(sortedCoordinates, coordinate)

                    require(rightNeighbour != null && belowNeighbour != null) {
                        "At least one neighbours is null: right: $rightNeighbour / below: $belowNeighbour"
                    }

                    connections.add(
                        Connection(
                            coordinate,
                            rightNeighbour,
                            Direction.HORIZONTAL,
                            IntRange(coordinate.x, rightNeighbour.x)
                        )
                    )
                    connections.add(
                        Connection(
                            coordinate,
                            belowNeighbour,
                            Direction.VERTICAL,
                            IntRange(coordinate.y, belowNeighbour.y)
                        )
                    )
                }

                1 -> {
                    when (connectionsToCoordinate.first().direction) {
                        Direction.HORIZONTAL -> {
                            val belowNeighbour = belowNeighbour(sortedCoordinates, coordinate)

                            require(belowNeighbour != null) {
                                "Right neighbours is null"
                            }

                            connections.add(
                                Connection(
                                    coordinate,
                                    belowNeighbour,
                                    Direction.VERTICAL,
                                    IntRange(coordinate.y, belowNeighbour.y)
                                )
                            )
                        }

                        Direction.VERTICAL -> {
                            val rightNeighbour = rightNeighbour(sortedCoordinates, coordinate)

                            require(rightNeighbour != null) {
                                "Below neighbours is null"
                            }

                            connections.add(
                                Connection(
                                    coordinate,
                                    rightNeighbour,
                                    Direction.HORIZONTAL,
                                    IntRange(coordinate.x, rightNeighbour.x)
                                )
                            )
                        }
                    }
                }
            }
        }

        connections.forEach { println(it) }
    }

    private fun rightNeighbour(connections: List<Coordinate>, coordinate: Coordinate): Coordinate? {
        return connections
            .filter { it.y == coordinate.y && it.x > coordinate.x }
            .minByOrNull { it.x }
    }

    private fun belowNeighbour(connections: List<Coordinate>, coordinate: Coordinate): Coordinate? {
        return connections
            .filter { it.x == coordinate.x && it.y > coordinate.y }
            .minByOrNull { it.y }
    }

    private fun connectionsToCoordinate(
        connections: List<Connection>,
        coordinate: Coordinate
    ): List<Connection> {
        val results = connections.filter { it.first == coordinate || it.second == coordinate }

        require(
            results.size != 2 ||
                    (results.count { it.direction == Direction.HORIZONTAL } != 2 &&
                            results.count { it.direction == Direction.VERTICAL } != 2)
        ) {
            "Exactly two results must not have the same direction"
        }

        require(results.size <= 2) { "To many connections to one point" }

        return results
    }
}

data class Coordinate(
    val x: Int,
    val y: Int,
)

data class Area(
    val first: Coordinate,
    val second: Coordinate,
    val volume: Long,
)

enum class Direction {
    HORIZONTAL,
    VERTICAL;
}

data class Connection(
    val first: Coordinate,
    val second: Coordinate,
    val direction: Direction,
    val range: IntRange,
)