package aoc.backend.service.file

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class FileConverter {

    fun convertMultiPartToList(multipartFile: MultipartFile): List<String> {
        return multipartFile.inputStream.bufferedReader(Charsets.UTF_8).use { reader ->
            reader.readLines()
        }
    }
}