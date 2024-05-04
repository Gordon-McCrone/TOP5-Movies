package com.example.top5_movies

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity


class MainActivitySearchResults : AppCompatActivity() {
    lateinit var background: RelativeLayout

    var searchResults: MutableList<MovieItemsSearchResults> = ArrayList()

    lateinit var adapter: MovieItemsSearchResultsAdapter
    lateinit var movieItemsSearchResultsView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results)

        background = RelativeLayout(this)
        background.setBackgroundColor(Color.parseColor("#F9F9F9"))
        val backgroundLayoutParam = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.MATCH_PARENT
        )
        // set RelativeLayout as a root element of the screen
        setContentView(background, backgroundLayoutParam)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        val extra = this.intent.getParcelableArrayListExtra<MovieItemsSearchResults>("searchResults")
        searchResults = extra as ArrayList<MovieItemsSearchResults>
        //searchResults = this.intent.getParcelableArrayListExtra<Parcelable>("searchResults") as MutableList<MovieItemsSearchResults>

        movieItemsSearchResultsView = ListView(this)
        val rel_movieItemsSearchResultsView = RelativeLayout.LayoutParams(displayMetrics.widthPixels,displayMetrics.heightPixels)
        //rel_discoveryListView.topMargin
        movieItemsSearchResultsView.setLayoutParams(rel_movieItemsSearchResultsView)
        // Paste this into Android studio and it automatically converts (0xFFC9C9CE to -0x363632), first 2 F's opacity, not last 2 F's, and first number and third number are colours to blend with: "int[] colors = {0, 0xFFC9C9CE, 0};"
        val colors = intArrayOf(-0x363632, -0x363632, -0x363632)
        movieItemsSearchResultsView.setDivider(GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors))
        movieItemsSearchResultsView.setDividerHeight(1);
        background.addView(movieItemsSearchResultsView)

        adapter = MovieItemsSearchResultsAdapter(this, searchResults)
        movieItemsSearchResultsView.setAdapter(adapter)
    }
}