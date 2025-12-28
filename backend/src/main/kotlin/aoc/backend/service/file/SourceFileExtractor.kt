package aoc.backend.service.file

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream

@Service
class SourceFileExtractor {

    // TODO: Add cache
    fun getResourceFileAsString(fileName: String): String {
        return getResourceFileAsInputStream(fileName).use { inputStream ->
            if (inputStream == null) throw IllegalArgumentException("File not found: $fileName")
            BufferedReader(inputStream.reader()).use {
                it.readText()
            }
        }
    }

    fun getResourceFileAsInputStream(fileName: String): InputStream? {
        val classLoader = this.javaClass.classLoader
        return classLoader.getResourceAsStream(fileName);
    }

}