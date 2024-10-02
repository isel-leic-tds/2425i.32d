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

/*  private class It<U>(private var n: Node<U>?) : Iterator<U> {
        override fun hasNext() = n!=null
        override fun next(): U =
            (n?.also { n = it.next } ?: throw NoSuchElementException()).elem
    } */
    private inner class It(private var n: Node<T>? =head) : Iterator<T> {
        override fun hasNext() = n!=null
        override fun next(): T =
            (n?.also { n = it.next } ?: throw NoSuchElementException()).elem
    }
    override fun iterator(): Iterator<T> = It()

    /*
    fun forEach(action: (T) -> Unit) {
        var n = head
        while (n!=null) {
            action(n.elem)
            n = n.next
        }
    }
    fun <R> map(transform: (T) -> R): List<R> {
        val res = mutableListOf<R>()
        var n = head
        while (n!=null) {
            res.add(transform(n.elem))
            n = n.next
        }
        return res
    }
    */

    companion object {
        val name = "Stack"
        fun fx() = 42
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stack<*>) return false
        var n1 = head
        var n2 = other.head
        while (n1!=null && n2!=null) {
            if (n1.elem != n2.elem) return false
            n1 = n1 . next
            n2 = n2.next
        }
        return n1==null && n2==null
    }
    override fun hashCode(): Int {
        var n = head
        var hash = 0
        while (n!=null) {
            hash = hash * 31 + n.elem.hashCode()
            n = n.next
        }
        return hash
    }
}