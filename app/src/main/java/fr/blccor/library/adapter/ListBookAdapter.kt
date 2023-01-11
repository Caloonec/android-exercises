package fr.blccor.library.adapter

import androidx.recyclerview.widget.RecyclerView
import fr.blccor.library.BookViewHolder
import fr.blccor.library.model.Book
// Classe abstrait de book adapter generique pour affichage en liste
abstract class ListBookAdapter: RecyclerView.Adapter<BookViewHolder>() {
    abstract fun setOnBookSelectedListener(listener: OnBookSelectedListener)
    abstract fun updateBooks(books: List<Book>)
}