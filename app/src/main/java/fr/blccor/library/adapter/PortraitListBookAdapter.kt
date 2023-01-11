package fr.blccor.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import fr.blccor.library.BookViewHolder
import fr.blccor.library.R
import fr.blccor.library.model.Book

class PortraitListBookAdapter(
    private val books: MutableList<Book>
) : ListBookAdapter() {

    // ------ Listener pour gérer la clic sur le livre pour voir les détails  ------
    private var listener: OnBookSelectedListener? = null

    override fun setOnBookSelectedListener(listener: OnBookSelectedListener) {
        this.listener = listener
    }
    // -----------------------------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.portrait_list_item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun getItemCount() = books.size

    // Initialisation de l'affichage de chaque livre de la vue liste portrait
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        // Mise à jour des informations de la vue
        val book = books[position]
        holder.titleTextView.text = book.title
        holder.priceTextView.text = book.price.toString() + "€"
        if (book.cover.isNotEmpty()) {
            Picasso.get()
                .load(book.cover)
                .into(holder.coverImageView)
        }

        // Mise en place du listener pour afficher les détails du livre
        holder.itemView.setOnClickListener {
            listener?.onBookSelected(book)
        }
    }

    override fun updateBooks(newBooks: List<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }
}