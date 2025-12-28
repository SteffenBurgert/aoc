package main

import (
	"fmt"
	"go-aoc/util"
	"strconv"
	"strings"
)

var currPos = 50
var counter1 = 0
var counter2 = 0

func main() {
	input := util.ReadFile("inputs/day1_1.txt")
	Day1(input)
}

func Day1(input string) {
	rotation := strings.Split(input, "\n")
	fmt.Printf("Start point: %d", currPos)
	for _, line := range rotation {
		dir := line[0:1]
		amount := line[1:]
		value, _ := strconv.Atoi(amount)
		move(dir, value)
		if currPos == 0 {
			counter1++
			counter2++
		}
		fmt.Printf("Current pos: %d, moved %s%s \n", currPos, dir, amount)
		fmt.Printf("counter 1: %d \n", counter1)
		fmt.Printf("counter 2: %d \n", counter2)
	}
	fmt.Printf("Password part one: %d", counter1)
	fmt.Println()
	fmt.Printf("Password part two: %d", counter2)
}

func move(dir string, amount int) {
	if dir == "L" {
		// Part two
		rotations := amount / 100
		counter2 += rotations
		rest := amount - rotations*100
		if currPos-rest < 0 && currPos != 0 {
			counter2++
		}

		// Part one
		if reminder := (currPos - amount) % 100; reminder < 0 {
			reminder += 100
			currPos = reminder
		} else {
			currPos = reminder
		}
	} else {
		// Part two
		rotations := amount / 100
		counter2 += rotations
		rest := amount - rotations*100
		if currPos+rest > 100 && currPos != 0 {
			counter2++
		}

		// Part one
		currPos = (currPos + amount) % 100
	}
}
