package fr.blccor.library

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView = itemView.findViewById<TextView>(R.id.title_text_view)
    val priceTextView = itemView.findViewById<TextView>(R.id.price_text_view)
    val coverImageView = itemView.findViewById<ImageView>(R.id.cover_image_view)
    val deleteImageButton = itemView.findViewById<ImageButton>(R.id.delete)
    val cartButton = itemView.findViewById<ImageButton>(R.id.add_to_cart)
    val synopsisTextView = itemView.findViewById<TextView>(R.id.synopsis)
}