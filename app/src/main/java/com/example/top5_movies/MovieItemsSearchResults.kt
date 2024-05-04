package com.example.top5_movies

import android.os.Parcel
import android.os.Parcelable

data class MovieItemsSearchResults(val title: String, val release_date: String, val backdrop_path: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(release_date)
        parcel.writeString(backdrop_path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieItemsSearchResults> {
        override fun createFromParcel(parcel: Parcel): MovieItemsSearchResults {
            return MovieItemsSearchResults(parcel)
        }

        override fun newArray(size: Int): Array<MovieItemsSearchResults?> {
            return arrayOfNulls(size)
        }
    }
}