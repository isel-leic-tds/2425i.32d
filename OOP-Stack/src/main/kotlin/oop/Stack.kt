package oop

interface Stack<T> : Iterable<T> {
    val top: T
    fun push(e: T): Stack<T>
    fun pop(): Stack<T>
    fun isEmpty(): Boolean
}

fun <T> Stack() = StackEmpty as Stack<T>

private class Node<T>(val elem: T, val next: Node<T>?)

private object StackEmpty : Stack<Any> {
    private fun throwEmpty(): Nothing { throw NoSuchElementException("Satck Empty")}
    override val top: Nothing get() = throwEmpty()
    override fun push(e: Any): Stack<Any> = StackNotEmpty(Node(e,null))
    override fun pop() = throwEmpty()
    override fun isEmpty() = true
    override fun iterator() = object : Iterator<Nothing> {
        override fun hasNext() = false
        override fun next(): Nothing = throwEmpty()
    }
}

private class StackNotEmpty<T>(val head: Node<T>) : Stack<T> {
    override val top: T get() = head.elem
    override fun push(e: T): Stack<T> = StackNotEmpty(Node(e,head))
    override fun pop(): Stack<T> =
        if (head.next==null) StackEmpty as Stack<T> else StackNotEmpty(head.next)
    override fun isEmpty(): Boolean = false
    override fun iterator() = object : Iterator<T> {
        var n: Node<T>? = head
        override fun hasNext() = n!=null
        override fun next(): T =
            (n?.also { n = it.next } ?: throw NoSuchElementException()).elem
    }
    override fun equals(other: Any?): Boolean {
        if (other !is StackNotEmpty<*>) return false
        return equalNodes(head, other.head as Node<T>?)
    }
    private tailrec fun equalNodes(n1: Node<T>?, n2: Node<T>?): Boolean = when {
        n1 == null -> n2 == null
        n2 == null || n1.elem != n2.elem -> false
        else -> equalNodes(n1.next,n2.next)
    }
    override fun hashCode(): Int =
        fold(0){ hash, e -> 31*hash + e.hashCode() }
}