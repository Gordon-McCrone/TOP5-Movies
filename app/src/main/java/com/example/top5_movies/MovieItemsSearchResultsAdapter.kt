package com.example.top5_movies

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.top5_movies.databinding.SearchResultsBinding

class MovieItemsSearchResultsAdapter(context: Context, contacts: List<MovieItemsSearchResults>)
    : ArrayAdapter<MovieItemsSearchResults>(context, 0, contacts) {

    private lateinit var binding: SearchResultsBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentMovieItem = getItem(position)

        binding = SearchResultsBinding.inflate(LayoutInflater.from(context))

        var shortYear = "N/A"

        binding.releaseDateTitle.setTextColor(Color.BLACK)
        if (currentMovieItem?.release_date!!.length > 0) {
            shortYear = (currentMovieItem?.release_date)?.substring(0, 4).toString()
        }
        binding.releaseDateTitle.text = currentMovieItem?.title + " (" + shortYear + ")"

        return binding.root
    }
}