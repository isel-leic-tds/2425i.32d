package isel.leic.tds.storage

/**
 * Storage in memory to use in tests.
 * Internally use a mutable map.
 */
class MemoryStorage<K,D: Any>: Storage<K,D> {
    private val map: MutableMap<K,D> = mutableMapOf()

    override fun create(key: K, data: D) {
        check(key !in map) { "$key already exists" }
        map[key]=data
    }
    override fun read(key: K): D? = map[key]

    override fun update(key: K, data: D) {
        check(key in map) { "$key not exists" }
        map[key]=data
    }
    override fun delete(key: K) {
        checkNotNull(map.remove(key)) {"$key not exists"}
    }
}