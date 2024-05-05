package com.example.top5_movies

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


class MainActivitySearchResults : AppCompatActivity() {
    lateinit var background: RelativeLayout

    var searchResults: MutableList<MovieItemsSearchResults> = ArrayList()

    lateinit var adapter: MovieItemsSearchResultsAdapter
    lateinit var movieItemsSearchResultsView: ListView
    lateinit var buttonGoBack: Button

    val iOSBlue = "#2D7CF6"
    val iOSGrey = "#F9F9F9"
    val iOSBrown = "#92683C"
    val iOSGreen = "#71FA4C"
    val iOSRed = "#EC3323"
    val iOSYellow = "#FFFD54"
    val iOSMagneta = "#EB3DF7"
    val iOSCyan = "#6FFBFD"
    val iOSOrange = "#F18633"
    val iOSGray = "#AAAAAA"

    val buttonHeight = 150

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_results)

        val buttonWidth = 300
        val bottomMargin = 80
        val leftMargin = 40
        val rightMargin = 40
        val buttonSpacer = 20

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

//        val extra = this.intent.getParcelableArrayListExtra<MovieItemsSearchResults>("searchResults")
//        searchResults = extra as ArrayList<MovieItemsSearchResults>
        searchResults = this.intent.getParcelableArrayListExtra<Parcelable>("searchResults") as MutableList<MovieItemsSearchResults>

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

        movieItemsSearchResultsView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val data = Intent()
            val currentMovieItem = parent.getItemAtPosition(position)
            currentMovieItem as MovieItemsSearchResults
            val testing: ArrayList<MovieItemsSearchResults> = ArrayList<MovieItemsSearchResults>()
            testing.add(currentMovieItem)
            data.putParcelableArrayListExtra("MovieItemsSearchResults", testing)

            setResult(Activity.RESULT_OK, data)
            //  as java.util.ArrayList<out Parcelable>
            finish()
        })

        buttonGoBack= Button(this)
//        button.setPadding(0,0,100,40)
        val rel_buttonGoBack = RelativeLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        rel_buttonGoBack.leftMargin = leftMargin // displayMetrics.widthPixels / 2 //- buttonWidth - rightMargin
        rel_buttonGoBack.topMargin = displayMetrics.heightPixels - buttonHeight - bottomMargin
        rel_buttonGoBack.width = buttonWidth
        rel_buttonGoBack.height = buttonHeight
        buttonGoBack.setLayoutParams(rel_buttonGoBack)
        buttonGoBack.text = "Go Back"
        buttonGoBack.setOnClickListener{
            finish()
        }
        buttonGoBack.setTextColor(Color.parseColor("#000000"))
        buttonGoBack.visibility = View.VISIBLE
        val gdButtonGoBack = GradientDrawable()
        gdButtonGoBack.setColor(Color.parseColor(iOSYellow)) // Changes this drawable to use a single color instead of a gradient
        gdButtonGoBack.cornerRadius = 20f
        gdButtonGoBack.setStroke(5, Color.parseColor("#000000"))
        buttonGoBack.background = gdButtonGoBack
        background.addView(buttonGoBack)
    }
}