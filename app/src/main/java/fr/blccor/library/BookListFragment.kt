package fr.blccor.library

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.blccor.library.adapter.LandscapeListBookAdapter
import fr.blccor.library.adapter.LandscapeListBookAdapter.OnCartAddListener
import fr.blccor.library.adapter.ListBookAdapter
import fr.blccor.library.adapter.PortraitListBookAdapter
import fr.blccor.library.api.ApiClient
import fr.blccor.library.api.BookService
import fr.blccor.library.model.Book
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import fr.blccor.library.adapter.OnBookSelectedListener as OnBookSelectedListenerInterface

class BookListFragment : Fragment() {

    private lateinit var bookRecyclerView: RecyclerView
    private lateinit var listBookAdapter: ListBookAdapter
    private lateinit var bookService: BookService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_book_list, container, false)

        bookRecyclerView = rootView.findViewById(R.id.book_recycler_view)
        bookRecyclerView.layoutManager = LinearLayoutManager(activity)
        val emptyMutableList = mutableListOf<Book>()

        // Affichage conditionnel selon l'orientation de l'écran
        when (resources.configuration.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> listBookAdapter = PortraitListBookAdapter(emptyMutableList)
            Configuration.ORIENTATION_LANDSCAPE -> {
                // Définition du comportement du bouton d'ajour au panier sur l'écran liste de livres en mode paysage
                listBookAdapter = LandscapeListBookAdapter(emptyMutableList)
                (listBookAdapter as LandscapeListBookAdapter).setOnCartAddListener(object : OnCartAddListener {
                    override fun onCartAdd(book: Book) {
                        (activity as LibraryActivity).onCartAdd(book)
                    }
                })
            }
        }

        // Définition du comportement au clic sur le livre
        listBookAdapter.setOnBookSelectedListener(object : OnBookSelectedListenerInterface {
            override fun onBookSelected(book: Book) {
                    val transaction = parentFragmentManager.beginTransaction()
                    transaction.replace(R.id.container, BookDetailFragment.newInstance(book))
                    transaction.addToBackStack(null)
                    transaction.commit()
            }
        })
        bookRecyclerView.adapter = listBookAdapter

        // Gestion de l'appel à l'API Rest
        bookService = ApiClient.create()
        bookService.getBooks().enqueue(object : Callback<List<Book>> {
            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                listBookAdapter.updateBooks(response.body() ?: emptyList())
            }
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                println(t.message)
            }
        })

        return rootView
    }

    companion object {
        fun newInstance() = BookListFragment()
    }
}
