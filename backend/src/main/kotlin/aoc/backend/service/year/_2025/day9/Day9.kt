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
        val biggestArea = calculateAreasSortedByVolumeDesc(coordinates).first()

        return biggestArea.volume
    }

    override fun part2(lines: List<String>): Long {
        val coordinates = getCoordinates(lines)
        val areas = calculateAreasSortedByVolumeDesc(coordinates)
        val border = getBordersOfShape(coordinates)

        return biggestAreaInShape(areas, border).volume
    }

    private fun getCoordinates(lines: List<String>): List<Coordinate> {
        return lines.map { line ->
            line.split(",").map(String::toInt)
                .run { Coordinate(this[0], this[1]) }
        }
    }

    private fun calculateAreasSortedByVolumeDesc(coordinates: List<Coordinate>): List<Area> {
        return coordinates.flatMapIndexed { index, coordinate ->
            coordinates.drop(index + 1).map { nextCoordinate ->
                val volume: Long = calculateVolume(coordinate, nextCoordinate)
                Area(coordinate, nextCoordinate, volume)
            }
        }.sortedByDescending { it.volume }
    }

    private fun calculateVolume(first: Coordinate, second: Coordinate): Long {
        return (abs(first.x - second.x) + 1L) * (abs(first.y - second.y) + 1L)
    }

    private fun getBordersOfShape(coordinates: List<Coordinate>): List<Connection> {
        val sortedCoordinates =
            coordinates.sortedWith(compareBy<Coordinate> { it.y }.thenBy { it.x })

        val connections = mutableListOf<Connection>()

        sortedCoordinates.forEach { coordinate ->
            getBorderForCoordinate(connections, coordinate, sortedCoordinates)
        }

        return connections
    }

    private fun getBorderForCoordinate(
        connections: MutableList<Connection>,
        coordinate: Coordinate,
        sortedCoordinates: List<Coordinate>
    ) {
        val connectionsToCoordinate = connectionsToCoordinate(connections, coordinate)

        when (connectionsToCoordinate.size) {
            0 -> {
                addRightNeighbour(sortedCoordinates, coordinate, connections)
                addBelowNeighbour(sortedCoordinates, coordinate, connections)
            }

            1 -> addConnectionDependingOnDirection(
                connectionsToCoordinate.first(),
                sortedCoordinates,
                coordinate,
                connections
            )
        }
    }

    private fun addConnectionDependingOnDirection(
        connectionsToCoordinate: Connection,
        sortedCoordinates: List<Coordinate>,
        coordinate: Coordinate,
        connections: MutableList<Connection>
    ) {
        when (connectionsToCoordinate.direction) {
            Direction.HORIZONTAL -> addBelowNeighbour(sortedCoordinates, coordinate, connections)
            Direction.VERTICAL -> addRightNeighbour(sortedCoordinates, coordinate, connections)
        }
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

    private fun addRightNeighbour(
        coordinates: List<Coordinate>,
        coordinate: Coordinate,
        connections: MutableList<Connection>
    ) {
        val rightNeighbour = findRightNeighbour(coordinates, coordinate)

        require(rightNeighbour != null) { "Right neighbours is null" }

        connections.add(Connection(coordinate, rightNeighbour, Direction.HORIZONTAL))
    }

    private fun findRightNeighbour(
        connections: List<Coordinate>,
        coordinate: Coordinate
    ): Coordinate? {
        return connections
            .filter { it.y == coordinate.y && it.x > coordinate.x }
            .minByOrNull { it.x }
    }

    private fun addBelowNeighbour(
        coordinates: List<Coordinate>,
        coordinate: Coordinate,
        connections: MutableList<Connection>
    ) {
        val belowNeighbour = findBelowNeighbour(coordinates, coordinate)

        require(belowNeighbour != null) { "Below neighbours is null" }

        connections.add(Connection(coordinate, belowNeighbour, Direction.VERTICAL))
    }

    private fun findBelowNeighbour(
        connections: List<Coordinate>,
        coordinate: Coordinate
    ): Coordinate? {
        return connections
            .filter { it.x == coordinate.x && it.y > coordinate.y }
            .minByOrNull { it.y }
    }

    private fun biggestAreaInShape(areas: List<Area>, border: List<Connection>): Area {
        val horizontalConnections = border.filter { it.direction == Direction.HORIZONTAL }
        val verticalConnection = border.filter { it.direction == Direction.VERTICAL }

        areas.forEach { area ->
            val rectangle = defineRectangle(area)

            if (anyCoordinateInArea(border, rectangle)) {
                return@forEach
            }

            if (anyHorizontalConnectionThroughArea(rectangle, horizontalConnections)) {
                return@forEach
            }

            if (anyVerticalConnectionThroughArea(rectangle, verticalConnection)) {
                return@forEach
            }

            return area
        }

        throw NoSuchElementException("There is no suitable area")
    }

    private fun defineRectangle(area: Area): Rectangle {
        val left = minOf(area.first.x, area.second.x)
        val right = maxOf(area.first.x, area.second.x)
        val bottom = maxOf(area.first.y, area.second.y)
        val top = minOf(area.first.y, area.second.y)

        return Rectangle(
            topLeft = Coordinate(left, top),
            topRight = Coordinate(right, top),
            bottomLeft = Coordinate(left, bottom),
            bottomRight = Coordinate(right, bottom),
        )
    }

    private fun anyCoordinateInArea(border: List<Connection>, rectangle: Rectangle): Boolean {
        return border.any {
            (it.first.x > rectangle.topLeft.x && it.first.x < rectangle.topRight.x
                    && it.first.y > rectangle.topLeft.y && it.first.y < rectangle.bottomLeft.y)
                    || (it.second.x > rectangle.topLeft.x && it.second.x < rectangle.topRight.x
                    && it.second.y > rectangle.topLeft.y && it.second.y < rectangle.bottomLeft.y)
        }
    }

    private fun anyHorizontalConnectionThroughArea(
        rectangle: Rectangle,
        horizontalConnections: List<Connection>,
    ): Boolean {
        val coordinatesOnYLevelOfArea = horizontalConnections.filter {
            (it.first.y > rectangle.topLeft.y && it.first.y < rectangle.bottomLeft.y)
                    || (it.second.y > rectangle.topLeft.y && it.second.y < rectangle.bottomLeft.y)
        }

        return coordinatesOnYLevelOfArea.any {
            it.first.x <= rectangle.topLeft.x && it.second.x >= rectangle.topRight.x
        }
    }

    private fun anyVerticalConnectionThroughArea(
        rectangle: Rectangle,
        verticalConnection: List<Connection>
    ): Boolean {
        val coordinatesOnXLevelOfArea = verticalConnection.filter {
            (it.first.x > rectangle.topLeft.x && it.first.x < rectangle.topRight.x)
                    && (it.second.x > rectangle.topLeft.x && it.second.x < rectangle.topRight.x)
        }

        return coordinatesOnXLevelOfArea.any {
            it.first.y <= rectangle.topRight.y && it.second.y >= rectangle.bottomLeft.y
        }
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
)

data class Rectangle(
    val topLeft: Coordinate,
    val topRight: Coordinate,
    val bottomLeft: Coordinate,
    val bottomRight: Coordinate,
)