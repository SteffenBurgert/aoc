package aoc.backend.year

import java.io.File

interface Day {

    fun part1(file: File): Long

    fun part2(file: File): Long
}