package linkedlist

class MutableStack<T> {
    private class Node<E>(val elem: E, val next:Node<E>? = null)
    private var head: Node<T>? = null
    private val first: Node<T> get() =
        head ?: throw NoSuchElementException("Empty stack")

    fun push(e: T) { head = Node(e,head) }
    fun pop(): T = first.also { head = it.next }.elem
    fun isEmpty() = head==null
    val top: T get() = first.elem
}