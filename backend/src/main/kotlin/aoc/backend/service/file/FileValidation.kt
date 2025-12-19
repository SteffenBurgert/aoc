package aoc.backend.service.file

import org.springframework.stereotype.Component

@Component
class FileValidation {

    companion object {
        fun validate(lines: List<String>, regex: Regex): Boolean {
            return lines.all { regex.matches(it) }
        }

        fun validate(lines: List<String>, regex: Regex, length: Int): Boolean {
            return lines.all { regex.matches(it) && length == it.length }
        }
    }
}