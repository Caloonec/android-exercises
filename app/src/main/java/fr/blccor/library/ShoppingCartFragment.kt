package fr.blccor.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.blccor.library.adapter.CartBookAdapter
import fr.blccor.library.model.Book
import fr.blccor.library.model.ShoppingCart
import java.util.*

class ShoppingCartFragment : Fragment() {

    private lateinit var shoppingCart: ShoppingCart
    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var cartBookAdapter: CartBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Initialisation vue
        val cartView = inflater.inflate(R.layout.fragment_shopping_cart, container, false)
        bookRecyclerView = cartView.findViewById(R.id.book_recycler_view)
        bookRecyclerView.layoutManager = LinearLayoutManager(activity)
        cartBookAdapter = CartBookAdapter(mutableListOf())
        bookRecyclerView.adapter = cartBookAdapter

        return cartView
    }

    interface OnBookAddedListener {
        fun onBookAdded(book: Book)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Récupération contenu panier
        shoppingCart = ShoppingCart()
        val books = arguments?.getParcelableArrayList<Book>(ARG_BOOKS) ?: arrayListOf()
        shoppingCart.getItems().addAll(books)
        updateUI(view)

        // Gestion comportement vider le panier
        cartBookAdapter.setOnBookDeleteListener(object : CartBookAdapter.OnBookDeleteListener {
            override fun onBookDelete(book: Book) {
                shoppingCart.deleteBook(book)
                updateUI(view)
            }
        })
    }

    // Mise à jour de l'affichage du panier
    private fun updateUI(view: View) {
        val totalPrice = shoppingCart.totalPrice
        view.findViewById<TextView>(R.id.total_price).text = totalPrice.toString() + "€"
        cartBookAdapter.updateBooks(shoppingCart.getItems())
        (activity as LibraryActivity).shoppingCart = shoppingCart
    }

    companion object {
        private const val ARG_BOOKS = "books"

        fun newInstance(books: ArrayList<Book>): ShoppingCartFragment {
            val fragment = ShoppingCartFragment()
            val args = Bundle()
            args.putParcelableArrayList(ARG_BOOKS, books)
            fragment.arguments = args
            return fragment
        }
    }
}