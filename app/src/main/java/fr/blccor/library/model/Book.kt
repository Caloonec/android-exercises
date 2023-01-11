package fr.blccor.library.model

import android.os.Parcel
import android.os.Parcelable

data class Book(
    val isbn: String,
    val title: String,
    val price: Int,
    val cover: String,
    val synopsis: List<String>
    ) : Parcelable {

    constructor(parcel: Parcel) : this (
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(title)
        parcel.writeInt(price)
        parcel.writeString(cover)
        parcel.writeStringList(synopsis)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getSynopsis(): String {
        var res = ""
        synopsis.forEach {
            res = res + it + "\n\n"
        }
        return res
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}