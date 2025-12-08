package _2025.day8

import readFile
import kotlin.math.pow
import kotlin.math.sqrt

data class Coordinate(
    val x: Int,
    val y: Int,
    val z: Int,
)

data class Connection(
    val first: Coordinate,
    val second: Coordinate,
    val distance: Double
)

fun main() {
    val coordinates = readFile("_2025/day8_input.txt").map { line ->
        line.split(",").map(String::toInt)
            .run { Coordinate(this[0], this[1], this[2]) }
    }

    println("Part 1: ${part1(coordinates)}")
}

private fun part1(coordinates: List<Coordinate>): Long {
    val shortestConnections = calculateShortestConnections(coordinates)

    val groups = findGroups(shortestConnections)

    return multiplyLargestThreeGroups(groups)
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
