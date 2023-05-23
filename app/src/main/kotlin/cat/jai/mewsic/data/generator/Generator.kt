package cat.jai.mewsic.data.generator

fun interface Generator<T> {
    fun next(): T?
}
fun <T> Generator<T>.toIterator(): Iterator<T> {
    var next = next()
    return object : Iterator<T> {
        override fun hasNext(): Boolean {
            return next != null
        }

        override fun next(): T {
            val ret = next ?: throw NoSuchElementException()
            next = this@toIterator.next()
            return ret
        }
    }
}
