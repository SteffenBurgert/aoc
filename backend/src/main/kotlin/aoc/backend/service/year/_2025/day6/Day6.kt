package aoc.backend.service.year._2025.day6

import aoc.backend.service.catalog.DayInfo
import aoc.backend.service.year.Day
import org.springframework.stereotype.Component

@Component("day6_2025")
@DayInfo(2025, 6)
class Day6(): Day {
    override fun part1(lines: List<String>): Long {
        return sumNumbers(lines)
    }

    override fun part2(lines: List<String>): Long {
        return sumCephalopod(lines)
    }

    private fun sumNumbers(lines: List<String>): Long {
        return extractNumbersAndOperatorFromFile(lines).sumOf {
            when (it.second) {
                '+' -> it.first.sum().toLong()
                '*' -> {
                    var result = 1L
                    it.first.forEach { num ->
                        result *= num
                    }
                    result
                }

                else -> 0L
            }
        }
    }

    private fun extractNumbersAndOperatorFromFile(file: List<String>): List<Pair<MutableList<Int>, Char>> {
        val numbers = mutableListOf<Pair<MutableList<Int>, Char>>()

        file.reversed().forEach { line ->
            line.split(" ").filter { it.isNotEmpty() && it.isNotBlank() }
                .forEachIndexed { index, element ->
                    if (numbers.size == index) numbers.add(Pair(mutableListOf(), element.first()))

                    element.toIntOrNull()?.let { numbers[index].first.add(it) }
                }
        }

        return numbers
    }

    private fun sumCephalopod(lines: List<String>): Long {
        var amount = 0L
        var operator = ' '
        var calculation = 0L
        for (i in 0 until lines.maxOf { it.length }) {
            updateOperator(lines, i, operator, calculation).let {
                operator = it.first
                calculation = it.second
            }

            updateCalculationAndAmount(buildNumber(lines, i), operator, calculation, amount).let {
                calculation = it.first
                amount = it.second
            }
        }

        return amount + calculation
    }

    private fun updateOperator(
        file: List<String>,
        i: Int,
        operator: Char,
        calculation: Long
    ): Pair<Char, Long> {
        return if (i < file[file.size - 1].length && file[file.size - 1][i] != ' ') {
            val newOperator = file[file.size - 1][i]

            val newCalculation = when (newOperator) {
                '+' -> 0L
                '*' -> 1L
                else -> throw IllegalArgumentException("Operator: $newOperator isn't '+' or '*'")
            }

            Pair(newOperator, newCalculation)
        } else Pair(operator, calculation)
    }

    private fun buildNumber(file: List<String>, i: Int): Long? {
        var number = ""
        for (digit in 0 until file.size - 1) {
            number += try {
                file[digit][i]
            } catch (_: Exception) {
                ' '
            }
        }

        return number.trim().toLongOrNull()
    }

    private fun updateCalculationAndAmount(
        number: Long?,
        operator: Char,
        calculation: Long,
        amount: Long
    ): Pair<Long, Long> {
        return if (number != null) {
            val result = when (operator) {
                '+' -> calculation + number
                '*' -> calculation * number
                else -> throw IllegalArgumentException("Operator: $operator isn't '+' or '*'")
            }

            Pair(result, amount)
        } else {
            Pair(calculation, amount + calculation)
        }
    }

}
