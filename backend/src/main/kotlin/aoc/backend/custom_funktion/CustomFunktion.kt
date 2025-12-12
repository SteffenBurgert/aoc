import java.io.File

/**
 * Reads whole file and returns a list of strings
 *
 * @param filename String
 * @return List<String>
 */
fun readFile(filename: String) = File("src/main/resources/$filename").readLines()

/**
 * Replaces a character at a specified index
 *
 * @receiver String
 * @param position Int: to change char
 * @param char Char: to insert
 * @return String with replaced char at position
 */
fun String.replaceCharOnString(position: Int, char: Char): String {
    return replaceChar(this, position, char)
}

/**
 * Replaces a character at a specified index
 *
 * @param string String
 * @param position Int
 * @param char Char
 * @return String
 */
fun replaceChar(string: String, position: Int, char: Char): String {
    val result = StringBuilder(string)
    if (position < string.length - 1) {
        result.replace(position, position + 1, char.toString())
    } else {
        result.deleteCharAt(position)
        result.append(char)
    }
    return result.toString()
}

/**
 * Returns the index of a value from a List<T>. It can be checked between a start and end index.
 * By default, the startIndex is 0 and the endIndex is List<T>.size.
 *
 * @receiver List<T>
 * @param value T: to find
 * @param startIndex Int: at which position to search
 * @param endIndex Int: where to end the search
 * @return Int: returns index if value exists and -1 if value couldn't be found
 */
fun <T> List<T>.indexOf(value: T, startIndex: Int = 0, endIndex: Int = this.size): Int {
    for (index in startIndex..<endIndex) {
        if (this[index] == value) {
            return index
        }
    }
    return -1
}
