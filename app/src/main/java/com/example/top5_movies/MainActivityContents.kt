package com.example.top5_movies

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivityContents : AppCompatActivity() {
    lateinit var background: RelativeLayout

    var movieItemsContents: MutableList<MovieItemsContents> = ArrayList()
    var movieItemsContentsUpdated: MutableList<MovieItemsContents> = ArrayList()

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

    lateinit var adapter: MovieItemsContentsAdapter
    lateinit var movieItemsContentsListView: ListView
    lateinit var buttonGoBack: Button
    lateinit var buttonSearch: Button

    var runOnceList = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movielist_contents)

        Log.d("*5*", "MainActivityContents onCreate")

        val buttonWidth = 300
        val bottomMargin = 80
        val leftMargin = 40
        val rightMargin = 40
        val buttonSpacer = 20

        //val background = RelativeLayout(this)
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

        movieItemsContentsListView = ListView(this)
        val rel_discoveryListView = RelativeLayout.LayoutParams(displayMetrics.widthPixels,displayMetrics.heightPixels)
        //rel_discoveryListView.topMargin
        movieItemsContentsListView.setLayoutParams(rel_discoveryListView)
        // Paste this into Android studio and it automatically converts (0xFFC9C9CE to -0x363632), first 2 F's opacity, not last 2 F's, and first number and third number are colours to blend with: "int[] colors = {0, 0xFFC9C9CE, 0};"
        val colors = intArrayOf(-0x363632, -0x363632, -0x363632)
        movieItemsContentsListView.setDivider(GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors))
        movieItemsContentsListView.setDividerHeight(1);
        background.addView(movieItemsContentsListView)

        setupMovieItemsContents()

        adapter = MovieItemsContentsAdapter(this, movieItemsContents)
        movieItemsContentsListView.setAdapter(adapter)

        movieItemsContentsListView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            //val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            //activityMovielistContentsBinding = .inflate(LayoutInflater.from(this))

//            val currentMovieItem = parent.getItemAtPosition(position)
//            currentMovieItem as MovieItems
//            activityMovielistContentsBinding.label.setText(currentMovieItem.name)
//            Log.d("*5*", "activityMovielistContentsBinding.label " + activityMovielistContentsBinding.label.toString())
//            Log.d("*5*", "currentMovieItem.name " + currentMovieItem.name)
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

        buttonSearch = Button(this)
//        button.setPadding(0,0,100,40)
        val rel_buttonSearch = RelativeLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        rel_buttonSearch.leftMargin = leftMargin + buttonWidth + buttonSpacer
        rel_buttonSearch.topMargin = displayMetrics.heightPixels - buttonHeight - bottomMargin
        rel_buttonSearch.width = buttonWidth
        rel_buttonSearch.height = buttonHeight
        buttonSearch.setLayoutParams(rel_buttonSearch)
        buttonSearch.text = "Search"
        buttonSearch.setOnClickListener{
            //onClickButtonUpdate()
        }
        buttonSearch.setTextColor(Color.parseColor("#000000"))
        buttonSearch.visibility = View.VISIBLE
        val gdButtonSearch = GradientDrawable()
        gdButtonSearch.setColor(Color.parseColor(iOSYellow)) // Changes this drawable to use a single color instead of a gradient
        gdButtonSearch.cornerRadius = 20f
        gdButtonSearch.setStroke(5, Color.parseColor("#000000"))
        buttonSearch.background = gdButtonSearch
        background.addView(buttonSearch)

        val movieItemSelected = intent.getStringExtra("currentMovieItem.name").toString()
        Log.d("*5*", "MainActivityContents movieItemSelected " + movieItemSelected)
        Log.d("*5*", "movieItemsContents count " + movieItemsContents.count())
        movieItemsContentsUpdated = movieItemsContents.filter({it.movieItem == movieItemSelected}).toMutableList()
        adapter = MovieItemsContentsAdapter(this, movieItemsContentsUpdated)
        movieItemsContentsListView.setAdapter(adapter)

        Log.d("*5*", "movieItemsContents count2: " + movieItemsContents.count())
    }

    fun setupMovieItemsContents() {
        Log.d("*5*", "setupMovieItemsContents runOnceList " + runOnceList)
        if (runOnceList == false) {
            movieItemsContents.add(MovieItemsContents("Action", "Diehard", "1988-07-22"))
            movieItemsContents.add(MovieItemsContents("Action", "The Terminator", "1984-10-26"))
            movieItemsContents.add(MovieItemsContents("Action", "Jurassic Park", "1993-08-20"))
            movieItemsContents.add(MovieItemsContents("Kids", "The Lion King", "1994-08-25"))
            movieItemsContents.add(MovieItemsContents("Kids", "Aladdin", "1992-11-25"))
            movieItemsContents.add(MovieItemsContents("Kids", "Bambi", "1942-08-21"))
            runOnceList = true
        }
    }
}