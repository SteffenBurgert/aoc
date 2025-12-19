package aoc.backend.service.year

interface Day {

    fun validate(lines: List<String>): Boolean

    fun part1(lines: List<String>): Long

    fun part2(lines: List<String>): Long
}