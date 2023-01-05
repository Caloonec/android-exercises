package fr.android.androidexercises

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.android.androidexercises.api.ApiClient
import fr.android.androidexercises.api.BookService
import fr.android.androidexercises.model.Book
import fr.android.androidexercises.model.ShoppingCart

class LibraryActivity : AppCompatActivity(), BookListFragment.OnBookSelectedListener,
    ShoppingCartFragment.OnBookAddedListener {

    private val bookService: BookService by lazy {
        ApiClient.createService(BookService::class.java)
    }

    private val shoppingCart = ShoppingCart()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        if (savedInstanceState == null) {
            navigateToFragment(BookListFragment.newInstance(), false)
        }
    }

    override fun onBookSelected(book: Book) {
        navigateToFragment(BookDetailFragment.newInstance(book), true)
    }

    override fun onBookAdded(book: Book) {
        shoppingCart.addBook(book)
    }

    private fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun showShoppingCart() {
        navigateToFragment(ShoppingCartFragment.newInstance(shoppingCart.getTotalPrice()), true)
    }
}