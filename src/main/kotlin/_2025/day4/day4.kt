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

private fun calculateAmountOfPossibleAccess(room: List<CharArray>, x: Int, y: Int): Int {
    val topLeft = if (y - 1 >= 0 && x - 1 >= 0 && room[y - 1][x - 1] == '@') 1 else 0
    val top = if (y - 1 >= 0 && room[y - 1][x] == '@') 1 else 0
    val topRight = if (y - 1 >= 0 && x + 1 < room[x].size && room[y - 1][x + 1] == '@') 1 else 0
    val left = if (x - 1 >= 0 && room[y][x - 1] == '@') 1 else 0
    val right = if (x + 1 < room[x].size && room[y][x + 1] == '@') 1 else 0
    val bottomLeft = if (x - 1 >= 0 && y + 1 < room.size && room[y + 1][x - 1] == '@') 1 else 0
    val bottom = if (y + 1 < room.size && room[y + 1][x] == '@') 1 else 0
    val bottomRight =
        if (x + 1 < room[x].size && y + 1 < room.size && room[y + 1][x + 1] == '@') 1 else 0

    return topLeft + top + topRight + left + right + bottomLeft + bottom + bottomRight
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