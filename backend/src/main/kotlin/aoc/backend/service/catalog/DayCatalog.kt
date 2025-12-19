package aoc.backend.service.catalog

import aoc.backend.adapter.dto.YearDto
import aoc.backend.adapter.dto.YearsDto
import aoc.backend.service.year.Day
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.collections.forEach

@Service
class DayCatalog(
    private val days: List<Day>
) {
    private lateinit var dayMap: Map<Pair<Int, Int>, Day>
    private lateinit var availableYears: YearsDto

    fun getDayImplementation(year: Int, day: Int): Day? =
        dayMap[year to day]

    val years: YearsDto
        get() = availableYears

    @PostConstruct
    private fun initialize() {
        val dayMap = mutableMapOf<Pair<Int, Int>, Day>()
        val availableYears = mutableMapOf<Int, MutableList<Int>>()

        days.forEach { day ->
            val info = day::class.java.getAnnotation(DayInfo::class.java)
                ?: run {
                    log.error("Missing @DayInfo on ${day::class.simpleName}")
                    return@forEach
                }

            val key = info.year to info.day

            require(key !in dayMap) { "Duplicate Day found for year=${info.year}, day=${info.day}" }

            dayMap[key] = day

            availableYears
                .getOrPut(info.year) { mutableListOf() }
                .add(info.day)
        }

        this.dayMap = dayMap.toMap()
        this.availableYears = YearsDto(
            availableYears
                .map { (year, days) -> YearDto(year, days.sorted()) }
                .sortedByDescending { it.year }
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }
}

