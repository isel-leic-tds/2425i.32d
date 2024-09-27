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

    override fun equals(other: Any?): Boolean {
        if (other !is MutableStack<*>) return false
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