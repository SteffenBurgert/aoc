package _2024.day1

import readFile
import kotlin.math.abs

fun main() {

    val left = mutableListOf<Int>()
    val right = mutableListOf<Int>()

    readFile("_2024/day1_input.txt").forEach { line ->
        line.split(Regex("\\s{3}")).map(String::toInt).let {
            left.add(it[0])
            right.add(it[1])
        }
    }

    left.sort()
    right.sort()

    val distance = left.mapIndexed { index, lVal -> abs(lVal - right[index]) }.sum()
    val totalAmount = left.sumOf { number -> number * right.count { number == it } }

    println("Part 1: $distance")
    println("Part 2: $totalAmount")
}