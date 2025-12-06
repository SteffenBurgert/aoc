fun main() {
    val room = mutableListOf<CharArray>()

    readFile("_2025/day4_input.txt").forEach { line ->
        room.add(line.toCharArray())
    }

    val possibleAccesses = determineForkliftsAccess(room).size
    val amountOfRemovablePapers = calculateAmountOfRemovedPapers(room)

    println("Part 1: $possibleAccesses")
    println("Part 2: $amountOfRemovablePapers")
}

private fun determineForkliftsAccess(room: List<CharArray>): List<Pair<Int, Int>> {
    val rollsOfPaper = mutableListOf<Pair<Int, Int>>()

    for (y in 0 until room.size) {
        for (x in 0 until room[0].size) {
            if (room[y][x] == '@' && calculateAmountOfPossibleAccess(room, x, y) < 4) {
                rollsOfPaper.add(Pair(x, y))
            }
        }
    }

    return rollsOfPaper
}

private fun calculateAmountOfPossibleAccess(room: List<CharArray>, xPos: Int, yPos: Int): Int {
    var amount = 0

    for (y in -1..1) {
        for (x in -1..1) {
            if (x == 0 && y == 0) continue

            val calculatedY = yPos + y
            val calculatedX = xPos + x

            if (
                calculatedY >= 0 && calculatedY < room.size &&
                calculatedX >= 0 && calculatedX < room.first().size &&
                room[calculatedY][calculatedX] == '@'
            ) {
                amount++
            }
        }
    }

    return amount
}

private fun calculateAmountOfRemovedPapers(room: List<CharArray>): Int {
    var amountOfRemovedPapers = 0
    var isRemovable = true
    val clearableRoom = room.toList()

    while (isRemovable) {
        determineForkliftsAccess(clearableRoom).let {
            removePaper(clearableRoom, it)
            amountOfRemovedPapers += it.size
            isRemovable = it.isNotEmpty()
        }
    }

    return amountOfRemovedPapers
}

private fun removePaper(room: List<CharArray>, positons: List<Pair<Int, Int>>) {
    positons.forEach { (x, y) -> room[y][x] = '.' }
}