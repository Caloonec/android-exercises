package fr.blccor.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import fr.blccor.library.model.Book

class BookDetailFragment : Fragment() {

    private lateinit var book: Book

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Initialisation des informations de la vue
        book = arguments?.getParcelable(ARG_BOOK) ?: Book("","",0,"", emptyList())
        view.findViewById<TextView>(R.id.title_text_view).text = book.title
        view.findViewById<TextView>(R.id.price_text_view).text = book.price.toString() + "€"
        view.findViewById<TextView>(R.id.synopsis_text_view).text = book.getSynopsis()
        if (book.cover.isNotEmpty()) {
            Picasso.get()
                .load(book.cover)
                .into(view.findViewById<ImageView>(R.id.cover_image_view))
        }

        // Définition du rôle du bouton d'ajout au panier
        view.findViewById<ImageButton>(R.id.add_to_cart).setOnClickListener{
            (activity as LibraryActivity).shoppingCart.addBook(book)
            Toast.makeText(activity, "Livre ajouté au panier !", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARG_BOOK = "book"

        fun newInstance(book: Book): BookDetailFragment {
            val fragment = BookDetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_BOOK, book)
            fragment.arguments = args
            return fragment
        }
    }
}