
fun main() {
    var start = 50
    var zeroAmount = 0
    var zeroOnRotation = 0

    readFile("_2025/day1_input.txt").forEach { line ->
        var movement = line.drop(1).toInt()

        val holeRounds = movement / 100
        zeroOnRotation += holeRounds
        movement -= 100 * holeRounds

        if (line.startsWith("L")) {
            if (start != 0 && start - movement < 0) zeroOnRotation++

            start = (start - movement) % 100
            if (start < 0) start += 100
        } else {
            if (start + movement > 100) zeroOnRotation++

            start = (start + movement) % 100
        }
        if (start == 0) zeroAmount++
    }

    println("Part 1: $zeroAmount")
    println("Part 2: ${zeroOnRotation + zeroAmount}")
}