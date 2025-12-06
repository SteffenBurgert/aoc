fun main() {
    val ranges = mutableListOf<LongRange>()
    val ids = mutableListOf<Long>()
    var freshIds = 0L
    var amount2 = 0L

    readFile("_2025/day5_input.txt").forEach { line ->
        if (line.contains('-')) {
            val (left, right) = line.split("-").map { it.toLong() }
            ranges.add(LongRange(left, right))
        } else if (line.isNotEmpty()) {
            ids.add(line.toLong())
        }
    }

    ids.forEach { id ->
        if (ranges.any { it.contains(id) }) {
            freshIds++
        }
    }

    ranges.sortBy { it.first }

    var min = ranges.first().first

    ranges.forEach { range ->
        if (range.last < min) return@forEach

        val start = if (range.first > min) range.first else min
        amount2 += range.last - start + 1

        min = range.last + 1
    }

    println("Part 1: $freshIds")
    println("Part 2: $amount2")
}
