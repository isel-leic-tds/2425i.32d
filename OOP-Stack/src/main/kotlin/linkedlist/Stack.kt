package linkedlist

class Stack<T> private constructor(private val head: Node<T>?) : Iterable<T> {
    private class Node<E>(val elem: E, val next:Node<E>? = null)
    private val first: Node<T> get() =
        head ?: throw NoSuchElementException("Empty stack")

    constructor(): this(null)
    fun push(e: T) = Stack(Node(e,head))
    fun pop() = Stack(first.next)
    fun isEmpty() = head==null
    val top: T get() = first.elem
    fun pop2() = top to pop() // top.to(pop())

    override fun iterator() = object : Iterator<T> {
        var n: Node<T>? =head
        override fun hasNext() = n!=null
        override fun next(): T =
            (n?.also { n = it.next } ?: throw NoSuchElementException()).elem
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stack<*>) return false
        val it = iterator()
        for(e in other)
            if (!it.hasNext() || e != it.next()) return false
        return !it.hasNext()
    }
    override fun hashCode(): Int =
        fold(0){ hash, e -> 31*hash + e.hashCode() }
}