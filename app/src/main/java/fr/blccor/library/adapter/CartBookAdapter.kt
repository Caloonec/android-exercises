package fr.blccor.library.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.blccor.library.BookViewHolder
import fr.blccor.library.R
import fr.blccor.library.model.Book

class CartBookAdapter(
    private val books: MutableList<Book>
) : RecyclerView.Adapter<BookViewHolder>() {

    // ------ Listener pour gérer la supression du panier ------
    interface OnBookDeleteListener {
        fun onBookDelete(book: Book)
    }

    fun setOnBookDeleteListener(listener: OnBookDeleteListener) {
        this.listener = listener
    }

    private var listener: OnBookDeleteListener? = null
    // ---------------------------------------------------------

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cart_item_book, parent, false)
        return BookViewHolder(itemView)
    }

    override fun getItemCount() = books.size

    // Initialisation de l'affichage de chaque livre de la vue panier
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
        // Mise en place l'interaction sur le bouton de suppression du panier
        holder.deleteImageButton.setOnClickListener{
            listener?.onBookDelete(book)
        }
    }

    fun updateBooks(newBooks: List<Book>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }
}