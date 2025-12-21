package aoc.backend.service.year._2025.day8

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.file.FileValidation
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component
import kotlin.math.pow
import kotlin.math.sqrt

@Component("day8_2025")
@DayInfo(2025, 8)
class Day8() : Day {
    override fun validate(lines: List<String>): Boolean {
        return FileValidation.validate(lines, Regex("\\d+,\\d+,\\d+"))
    }

    override fun part1(lines: List<String>): Long {
        val coordinates = getCoordinates(lines)
        val groups = getGroupedCoordinates(coordinates)

        return multiplyLargestThreeGroups(groups)
    }

    override fun part2(lines: List<String>): Long {
        val coordinates = getCoordinates(lines)
        val groups = getGroupedCoordinates(coordinates).toMutableList()

        val coordinatesNotInAGroup = mutableListOf<Coordinate>()
        coordinates.forEach { coordinate ->
            if (groups.none { group -> group.any { it.first == coordinate || it.second == coordinate } }) {
                coordinatesNotInAGroup.add(coordinate)
            }
        }

        while (coordinatesNotInAGroup.isNotEmpty()) {
            val coordinate = coordinatesNotInAGroup.removeFirst()

            var shortestDistance: Connection? = null
            var groupIndex = -1
            groups.forEachIndexed { index, group ->
                val groupCoordinates = mutableSetOf<Coordinate>()

                group.forEach {
                    groupCoordinates.add(it.first)
                    groupCoordinates.add(it.second)
                }

                groupCoordinates.forEach {
                    calculateDistance(it, coordinate).let { distance ->
                        if (distance < (shortestDistance?.distance ?: Double.MAX_VALUE)) {
                            shortestDistance = Connection(it, coordinate, distance)
                            groupIndex = index
                        }
                    }
                }
            }

            coordinatesNotInAGroup.forEach {
                calculateDistance(it, coordinate).let { distance ->
                    if (distance < (shortestDistance?.distance ?: Double.MAX_VALUE)) {
                        shortestDistance = Connection(it, coordinate, distance)
                        groupIndex = -1
                    }
                }
            }

            if (shortestDistance == null) throw NullPointerException("shortestDistance is null")

            if (groupIndex >= 0) {
                val test: MutableList<Connection> = groups.removeAt(groupIndex).toMutableList()
                test.add(
                    Connection(
                        shortestDistance.first,
                        shortestDistance.second,
                        shortestDistance.distance
                    )
                )

                groups.add(test)
            } else {
                groups.add(
                    listOf(
                        Connection(
                            shortestDistance.first,
                            shortestDistance.second,
                            shortestDistance.distance
                        )
                    )
                )
                coordinatesNotInAGroup.remove(shortestDistance.first)
            }
        }

        var max: Connection? = null
        groups.forEach { connections ->
            connections.forEach {
                if ((max?.distance ?: Double.MIN_VALUE) < it.distance) {
                    max = it
                }
            }
        }

        if (max == null) return -1

        return max.first.x.toLong() * max.second.x.toLong()
    }

    private fun getCoordinates(lines: List<String>): List<Coordinate> {
        return lines.map { line ->
            line.split(",").map(String::toInt)
                .run { Coordinate(this[0], this[1], this[2]) }
        }
    }

    private fun getGroupedCoordinates(coordinates: List<Coordinate>): List<List<Connection>> {
        val shortestConnections = calculateShortestConnections(coordinates)
        return findGroups(shortestConnections)
    }

    private fun calculateShortestConnections(coordinates: List<Coordinate>): MutableList<Connection> {
        val amountOfConnections = if (coordinates.size == 1000) 1000 else 10

        val shortestConnections = mutableListOf<Connection>()

        for (i in 0 until coordinates.size) {
            for (j in i + 1 until coordinates.size) {
                val distance = calculateDistance(coordinates[i], coordinates[j])

                addConnectionIfPossible(
                    shortestConnections,
                    amountOfConnections,
                    distance,
                    coordinates[i],
                    coordinates[j]
                )
            }
        }

        return shortestConnections
    }

    private fun calculateDistance(first: Coordinate, second: Coordinate): Double {
        return sqrt(
            (first.x - second.x).toDouble().pow(2) +
                    (first.y - second.y).toDouble().pow(2) +
                    (first.z - second.z).toDouble().pow(2)
        )
    }

    private fun addConnectionIfPossible(
        shortestConnections: MutableList<Connection>,
        amountOfConnections: Int,
        distance: Double,
        first: Coordinate,
        second: Coordinate,
    ) {
        val hit = when {
            shortestConnections.size < amountOfConnections -> true.also {
                shortestConnections.add(Connection(first, second, distance))
            }

            distance < shortestConnections.last().distance -> true.also {
                shortestConnections.removeLast()
                shortestConnections.add(Connection(first, second, distance))
            }

            else -> false
        }

        if (hit) shortestConnections.sortBy { it.distance }
    }

    private fun findGroups(shortestConnections: MutableList<Connection>): List<List<Connection>> {
        val groups = mutableListOf<MutableList<Connection>>()

        while (shortestConnections.isNotEmpty()) {
            val connectionGroup = mutableListOf(shortestConnections.removeFirst())

            do {
                val additionalConnection = shortestConnections.filter {
                    isConnectionInGroup(connectionGroup, it)
                }
                connectionGroup.addAll(additionalConnection)
                additionalConnection.forEach { shortestConnections.remove(it) }
            } while (additionalConnection.isNotEmpty())

            groups.add(connectionGroup)
        }

        return groups
    }

    private fun isConnectionInGroup(group: List<Connection>, connection: Connection): Boolean {
        return group.any {
            it.first == connection.first ||
                    it.first == connection.second ||
                    it.second == connection.first ||
                    it.second == connection.second
        }
    }

    private fun multiplyLargestThreeGroups(groups: List<List<Connection>>): Long {
        val uniqueJBoxes = defineAmountOfUniqueJBoxesPerGroups(groups)

        var multipliedLargestThreeGroups = 1L
        uniqueJBoxes.sortedDescending().stream().limit(3).toList().forEach {
            multipliedLargestThreeGroups *= it
        }

        return multipliedLargestThreeGroups
    }

    private fun defineAmountOfUniqueJBoxesPerGroups(groups: List<List<Connection>>): List<Int> {
        return groups.map { connections ->
            val uniqueJBox = mutableSetOf<Coordinate>()
            connections.forEach {
                uniqueJBox.add(it.first)
                uniqueJBox.add(it.second)
            }

            uniqueJBox.size
        }
    }
}

data class Coordinate(
    val x: Int,
    val y: Int,
    val z: Int,
)

data class Connection(
    val first: Coordinate,
    val second: Coordinate,
    val distance: Double,
)
