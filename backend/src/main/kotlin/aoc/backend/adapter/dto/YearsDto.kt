package aoc.backend.adapter.dto

data class YearsDto(
    val years: List<YearDto>
)

data class YearDto(
    val year: Int,
    val days: List<Int>
)
