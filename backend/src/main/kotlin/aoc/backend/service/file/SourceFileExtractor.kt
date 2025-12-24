package aoc.backend.service.file

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.InputStream

@Service
class SourceFileExtractor {

    // TODO: Add cache
    fun getResourceFileAsString(fileName: String): String {
        val inputStream = getResourceFileAsInputStream(fileName)
        if (inputStream != null) {
            val bufferReader = BufferedReader(inputStream.reader())
            return bufferReader.readText()
        } else {
           throw IllegalArgumentException("File not found")
        }
    }

    fun getResourceFileAsInputStream(fileName: String): InputStream? {
        val classLoader = this.javaClass.classLoader
        return classLoader.getResourceAsStream(fileName);
    }

}