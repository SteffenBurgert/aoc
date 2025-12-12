package _2025.day3

import readFile
import kotlin.math.pow

fun main() {

    var amountPart1 = 0L
    var amountPart2 = 0L

    readFile("_2025/day3_input.txt").forEach { line ->
        val lineList = line.chunked(1).map { it.toInt() }

        amountPart1 += calculateJoltage(lineList, 2)
        amountPart2 += calculateJoltage(lineList, 12)
    }

    println("Part 1: $amountPart1")
    println("Part 2: $amountPart2")
}

private fun calculateJoltage(lineList: List<Int>, resultSize: Int): Long {
    var result = 0L
    var start = 0

    for(i in 1.. resultSize) {
        val lastStart = start
        val list = lineList.subList(start, lineList.size - resultSize + i)
        val max = list.max()
        result += (10.0.pow(resultSize - i) * max).toLong()
        start = list.indexOf(max) + lastStart + 1
    }

    return result
}