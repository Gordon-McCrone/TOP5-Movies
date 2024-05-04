package com.example.top5_movies

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.top5_movies.databinding.ListviewItemBinding

class MovieItemsAdapter(context: Context, contacts: List<MovieItems>)
    : ArrayAdapter<MovieItems>(context, 0, contacts) {

    private lateinit var binding: ListviewItemBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentMovieItem = getItem(position)

        binding = ListviewItemBinding.inflate(LayoutInflater.from(context))

        binding.label.setTextColor(Color.BLACK)
        binding.label.text = currentMovieItem?.name

        return binding.root
    }
}