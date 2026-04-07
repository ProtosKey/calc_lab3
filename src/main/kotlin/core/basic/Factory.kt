package core.basic

interface Factory<T, R> {
    fun create(type: T): R
}
