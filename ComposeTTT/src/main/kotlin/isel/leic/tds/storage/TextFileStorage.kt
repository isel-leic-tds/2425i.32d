package isel.leic.tds.storage

import java.nio.file.*
import kotlin.io.path.*

/**
 * Storage system based on text files.
 * Each entry corresponds to a text file.
 * The key is the file name with ".txt" extension.
 * The value is the contents of the file serialized to text.
 * @param baseFolder the name of the folder where the files are stored.
 * @param serializer the serializer to convert data to text and vice-versa.
 */
class TextFileStorage<K, D: Any>(
    baseFolder: String,
    private val serializer: Serializer<D>,
): Storage<K,D> {
    private val basePath = Path(baseFolder)
    init {
        with(basePath) {
            if (exists())
                check(isDirectory()) { "$name is not a directory" }
            else createDirectory()
        }
    }

    private fun <T> withPath(key: K, fx: Path.()->T) =
        (basePath / "$key.txt").fx()

    // CRUD operation
    override fun create(key: K, data: D) = withPath(key){
        check(!exists()) { "$name already exists" }
        writeText( serializer.serialize(data) )
    }
    override fun read(key: K): D? = withPath(key) {
        try { serializer.deserialize(readText()) }
        catch (e: NoSuchFileException) { null }
    }
    override fun update(key: K, data: D) = withPath(key){
        check(exists()) { "$name does not exists" }
        writeText(serializer.serialize(data))
    }
    override fun delete(key: K) = withPath(key){
        check(deleteIfExists()) { "$name does not exists" }
    }
}