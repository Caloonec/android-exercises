package fr.blccor.library.adapter

import fr.blccor.library.model.Book

// Interface pour un listener d'ouverture de détails générique
interface OnBookSelectedListener {
    fun onBookSelected(book: Book)
}