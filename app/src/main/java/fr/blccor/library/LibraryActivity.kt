package fr.blccor.library

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import fr.blccor.library.adapter.LandscapeListBookAdapter
import fr.blccor.library.model.Book
import fr.blccor.library.model.ShoppingCart
import java.util.ArrayList

class LibraryActivity : AppCompatActivity(), ShoppingCartFragment.OnBookAddedListener, LandscapeListBookAdapter.OnCartAddListener {

    public var shoppingCart = ShoppingCart()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // On set la vue principale de l'application
        setContentView(R.layout.activity_library)

        // On affiche la vue de tous les livres qui est la vue par défaut (via fragment)
        if (savedInstanceState == null) {
            navigateToFragment(BookListFragment.newInstance(), false)
        }

        // On définit le comportement du bouton de panier
        findViewById<ImageButton>(R.id.cart_button).setOnClickListener {
            showShoppingCart()
        }
    }

    override fun onBookAdded(book: Book) {
        shoppingCart.addBook(book)
        Toast.makeText(this, "Livre ajouté au panier !", Toast.LENGTH_SHORT).show()
    }

    override fun onCartAdd(book: Book) {
        onBookAdded(book)
    }

    fun showShoppingCart() {
        navigateToFragment(ShoppingCartFragment.newInstance(shoppingCart.getItems() as ArrayList<Book>), true)
    }

    private fun navigateToFragment(fragment: Fragment, addToBackStack: Boolean) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }
}