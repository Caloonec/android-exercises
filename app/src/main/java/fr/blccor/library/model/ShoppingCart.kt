package fr.blccor.library.model;

class ShoppingCart {

    private val items = mutableListOf<Book>()

    val totalPrice: Int
    get() = items.sumOf { it.price }

    fun addBook(book: Book) {
        items.add(book)
    }

    fun deleteBook(book: Book) {
        items.remove(book)
    }

    fun clear() {
        items.clear()
    }

    fun getItems(): MutableList<Book> {
        return items
    }
}