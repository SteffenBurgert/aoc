package _2024.day2

import readFile
import kotlin.math.abs

fun main() {

    val list = readFile("_2024/day2_input.txt").map {
        it.split(" ").map(String::toInt)
    }

    var amount = 0
    val listPart2 = mutableListOf<List<Int>>()
    list.forEach {
        val sorted = it.sorted()
        if (sorted == it || sorted.reversed() == it) {
            var isSave = true

            for (i in 0..< sorted.lastIndex) {
                if (abs(sorted[i] - sorted[i + 1]) > 3 || sorted[i] == sorted[i + 1]) {
                    isSave = false
                    listPart2.add(it)
                    break
                }
            }
            if (isSave) {
                amount++
            }
        } else {
            listPart2.add(it)
        }
    }

    println(amount)

    listPart2.forEach {
        for (i in 0..it.lastIndex) {
            var isSave = true
            val bruteforce = it.toMutableList()
            bruteforce.removeAt(i)
            for (j in 0..< bruteforce.lastIndex) {
                if (abs(bruteforce[j] - bruteforce[j + 1]) > 3 || bruteforce[j] == bruteforce[j + 1]) {
                    isSave = false
                    break
                }
            }
            if (isSave) {
                val sorted = bruteforce.sorted()
                if (sorted == bruteforce || sorted.reversed() == bruteforce) {
                    amount++
                    break
                }
            }
        }
    }

    println(amount)
}