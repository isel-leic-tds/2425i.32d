package imutablelist

class MutableStack<T> {
    private var elems = emptyList<T>()

    fun push(e: T) { elems = elems + e }
    fun pop(): T = top.also{ elems = elems.dropLast(1) }
    fun isEmpty() = elems.isEmpty()
    val top: T get() = elems.last()
    override fun equals(other: Any?) =
        other is MutableStack<*> && elems==other.elems
    override fun hashCode() = elems.hashCode()
}