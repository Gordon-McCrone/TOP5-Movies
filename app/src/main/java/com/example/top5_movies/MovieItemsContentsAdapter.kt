package com.example.top5_movies

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.top5_movies.databinding.ActivityMovielistContentsBinding


class MovieItemsContentsAdapter(context: Context, contacts: List<MovieItemsContents>)
    : ArrayAdapter<MovieItemsContents>(context, 0, contacts) {

    private lateinit var binding: ActivityMovielistContentsBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val currentMovieItem = getItem(position)

        binding = ActivityMovielistContentsBinding.inflate(LayoutInflater.from(context))

        binding.releaseDateTitle.setTextColor(Color.BLACK)
        val shortYear = (currentMovieItem?.year)?.substring(0,4)
        binding.releaseDateTitle.text = currentMovieItem?.name + " (" + shortYear + ")"

        return binding.root
    }
}