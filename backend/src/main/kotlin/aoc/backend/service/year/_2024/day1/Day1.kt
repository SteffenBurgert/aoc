package aoc.backend.service.year._2024.day1

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component
import readFile
import kotlin.math.abs

@Component("day1_2024")
//@DayInfo(2024, 1)
class Day1() : Day {
    override fun part1(lines: List<String>): Long {
        TODO("Not yet implemented")
    }

    override fun part2(lines: List<String>): Long {
        TODO("Not yet implemented")
    }

    fun initialize() {

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

}
