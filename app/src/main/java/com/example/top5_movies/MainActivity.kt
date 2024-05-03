package com.example.top5_movies

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.top5_movies.databinding.ActivityMainBinding
import com.example.top5_movies.databinding.ListviewItemBinding
import com.example.top5_movies.databinding.NewStringInputBinding


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    lateinit var background: RelativeLayout

    var movieItems: MutableList<MovieItems> = ArrayList()
    //var movieItemsOriginal: MutableList<MovieItems> = ArrayList()

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

    lateinit var adapter: MovieItemsListAdapter
    lateinit var movieItemsListView: ListView
    lateinit var buttonCreate: Button

    private lateinit var bindingNewStringInputBinding: NewStringInputBinding

    enum class ButtonOperation {
        OPEN, UPDATE, DELETE
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listTapOperation = ButtonOperation.OPEN

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
            RelativeLayout.LayoutParams.MATCH_PARENT)
        // set RelativeLayout as a root element of the screen
        setContentView(background, backgroundLayoutParam)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)

        Log.d("*5*", "displayMetrics.heightPixels: ${displayMetrics.heightPixels}")

        movieItemsListView = ListView(this)
        val rel_discoveryListView = RelativeLayout.LayoutParams(displayMetrics.widthPixels,displayMetrics.heightPixels)
        //rel_discoveryListView.topMargin
        movieItemsListView.setLayoutParams(rel_discoveryListView)
        // Paste this into Android studio and it automatically converts (0xFFC9C9CE to -0x363632), first 2 F's opacity, not last 2 F's, and first number and third number are colours to blend with: "int[] colors = {0, 0xFFC9C9CE, 0};"
        val colors = intArrayOf(-0x363632, -0x363632, -0x363632)
        movieItemsListView.setDivider(GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors))
        movieItemsListView.setDividerHeight(1);
        background.addView(movieItemsListView)

        setupMovieItems()

        adapter = MovieItemsListAdapter(this, movieItems)

        movieItemsListView.setAdapter(adapter)

        movieItemsListView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        })

        movieItemsListView.setOnScrollListener(object : AbsListView.OnScrollListener {

            override
            fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
            }

            override
            fun onScroll(view: AbsListView, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
                Log.i("*5*", "onScroll ${firstVisibleItem}")
            }
        })

        buttonCreate = Button(this)
//        button.setPadding(0,0,100,40)
        val rel_buttonCreate = RelativeLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        rel_buttonCreate.leftMargin = leftMargin // displayMetrics.widthPixels / 2 //- buttonWidth - rightMargin
        rel_buttonCreate.topMargin = displayMetrics.heightPixels - buttonHeight - bottomMargin
        rel_buttonCreate.width = buttonWidth
        rel_buttonCreate.height = buttonHeight
        buttonCreate.setLayoutParams(rel_buttonCreate)
        buttonCreate.text = "Create"
        buttonCreate.setOnClickListener{
            Log.d("*5*", "buttonCreate.setOnClickListener")
            onClickButtonCreate()
        }
        buttonCreate.setTextColor(Color.parseColor("#000000"))
        buttonCreate.visibility = View.VISIBLE
        val gdButtonCreate = GradientDrawable()
        gdButtonCreate.setColor(Color.parseColor(iOSYellow)) // Changes this drawable to use a single color instead of a gradient
        gdButtonCreate.cornerRadius = 20f
        gdButtonCreate.setStroke(5, Color.parseColor("#000000"))
        buttonCreate.background = gdButtonCreate
        background.addView(buttonCreate)
    }
    fun setupMovieItems() {
        movieItems.add(MovieItems("Action"))
        movieItems.add(MovieItems("Romance"))
    }

    fun onClickButtonCreate() {

        Log.d("*5*", "onClickButtonCreate")

        bindingNewStringInputBinding = NewStringInputBinding.inflate(LayoutInflater.from(this))
        val view = bindingNewStringInputBinding.root
        setContentView(view)

        bindingNewStringInputBinding.buttonSave.setOnClickListener {
            if (bindingNewStringInputBinding.label.text.toString().length > 0) {
                movieItems.add(MovieItems(bindingNewStringInputBinding.label.text.toString()))
                setContentView(R.layout.activity_main)
                adapter.notifyDataSetChanged()
                setContentView(background)
            }
        }

        bindingNewStringInputBinding.buttonCancel.setOnClickListener{
            setContentView(R.layout.activity_main)
            setContentView(background)
        }
    }
}