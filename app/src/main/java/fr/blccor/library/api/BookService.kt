package fr.blccor.library.api

import fr.blccor.library.model.Book
import retrofit2.Call
import retrofit2.http.GET


interface BookService {
    @GET("/books")
    fun getBooks(): Call<List<Book>>
}