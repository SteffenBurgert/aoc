package aoc.backend

import aoc.backend.year._2025.day1.Day1
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@RestController
class YearController {

    @PostMapping("/upload/{year}/{day}")
    fun dayResult(
        @PathVariable year: Int,
        @PathVariable day: Int,
        @RequestParam("file") multipartFile: MultipartFile
    ): ResponseEntity<Pair<Long, Long>> {
        val file = convertMultiPartToFile(multipartFile)

        return ResponseEntity.ok(Pair(Day1().part1(file), Day1().part2(file)))
    }


    @Throws(IOException::class)
    private fun convertMultiPartToFile(multipartFile: MultipartFile): File {
        val file = File(multipartFile.originalFilename ?: throw IOException("File is null"))

        FileOutputStream(file).let {
            it.write(multipartFile.bytes)
            it.close()
        }

        return file
    }

}