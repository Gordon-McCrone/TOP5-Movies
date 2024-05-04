package com.example.top5_movies

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcelable
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
import com.example.top5_movies.databinding.NewSearchInputBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.http.HttpHeaders
import org.apache.http.ParseException
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.util.EntityUtils
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MainActivityContents : AppCompatActivity() {
    lateinit var background: RelativeLayout

    var movieItemsContents: MutableList<MovieItemsContents> = ArrayList()
    var movieItemsContentsUpdated: MutableList<MovieItemsContents> = ArrayList()
    var searchResults: MutableList<MovieItemsSearchResults> = ArrayList()

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

    private lateinit var bindingNewSearchInputBinding: NewSearchInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movielist_contents)

        Log.d("*5*", "MainActivityContents onCreate")

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

        movieItemsContentsListView = ListView(this)
        val rel_movieItemsContentsListView = RelativeLayout.LayoutParams(displayMetrics.widthPixels,displayMetrics.heightPixels)
        //rel_discoveryListView.topMargin
        movieItemsContentsListView.setLayoutParams(rel_movieItemsContentsListView)
        // Paste this into Android studio and it automatically converts (0xFFC9C9CE to -0x363632), first 2 F's opacity, not last 2 F's, and first number and third number are colours to blend with: "int[] colors = {0, 0xFFC9C9CE, 0};"
        val colors = intArrayOf(-0x363632, -0x363632, -0x363632)
        movieItemsContentsListView.setDivider(GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors))
        movieItemsContentsListView.setDividerHeight(1);
        background.addView(movieItemsContentsListView)

        setupMovieItemsContents()

        adapter = MovieItemsContentsAdapter(this, movieItemsContents)
        movieItemsContentsListView.setAdapter(adapter)

        movieItemsContentsListView.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->

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
            onClickButtonSearch()
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
        movieItemsContentsUpdated = movieItemsContents.filter({it.movieItem == movieItemSelected}).toMutableList()
        adapter = MovieItemsContentsAdapter(this, movieItemsContentsUpdated)
        movieItemsContentsListView.setAdapter(adapter)
    }

    fun setupMovieItemsContents() {
        movieItemsContents.add(MovieItemsContents("Action", "Diehard", "1988-07-22"))
        movieItemsContents.add(MovieItemsContents("Action", "The Terminator", "1984-10-26"))
        movieItemsContents.add(MovieItemsContents("Action", "Jurassic Park", "1993-08-20"))
        movieItemsContents.add(MovieItemsContents("Kids", "The Lion King", "1994-08-25"))
        movieItemsContents.add(MovieItemsContents("Kids", "Aladdin", "1992-11-25"))
        movieItemsContents.add(MovieItemsContents("Kids", "Bambi", "1942-08-21"))
    }

    fun onClickButtonSearch() {

        Log.d("*5*", "onClickButtonSearch")

        bindingNewSearchInputBinding = NewSearchInputBinding.inflate(LayoutInflater.from(this))
        val view = bindingNewSearchInputBinding.root
        setContentView(view)

        bindingNewSearchInputBinding.buttonSearch.setOnClickListener {
            if (bindingNewSearchInputBinding.label.text.toString().length > 0) {
//                movieItems.add(MovieItems(bindingNewStringInputBinding.label.text.toString()))
//                setContentView(R.layout.activity_main)
//                adapter.notifyDataSetChanged()
//                setContentView(background)

                //searchResults

                runBlocking {
                    GlobalScope.launch {
                        try {
                            val encodedURL = java.net.URLEncoder.encode(bindingNewSearchInputBinding.label.text.toString(), "utf-8")
                            val url = "https://api.themoviedb.org/3/search/movie?query=" + encodedURL

                            val httppost: HttpGet = HttpGet(url)
                            httppost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                            httppost.setHeader("Authorization", getString(R.string.TMDB_Authorization))
                            val httpclient: HttpClient = DefaultHttpClient()
                            val response = httpclient.execute(httppost)
                            val status = response.statusLine.statusCode
                            if (status == 200) {
                                val entity = response.entity
                                val data = EntityUtils.toString(entity)
                                val jsono = JSONObject(data)
                                val jarray = jsono.getJSONArray("results")
                                for (i in 0 until jarray.length()) {
                                    val objectInJSON = jarray.getJSONObject(i)
                                    val backdrop_path = objectInJSON.getString("backdrop_path")
                                    val title = objectInJSON.getString("title")
                                    val release_date = objectInJSON.getString("release_date")
                                    searchResults.add(MovieItemsSearchResults(title, release_date, backdrop_path))
                                }
                            }
                        } catch (e: JSONException) {
                            Log.e("Error :", e.message!!)
                        } catch (e: ParseException) {
                            Log.e("Error :", e.message!!)
                        } catch (e: IOException) {
                            Log.e("Error :", e.message!!)
                        } catch (e: Exception) {
                            Log.e("Error :", e.message!!)
                        }

                        Log.d("*5*", "searchResults: " + searchResults)
                    }
                }

                if (searchResults.count() > 0) {
                    runOnUiThread {
//                        val extra = Bundle()
//                        extra.putSerializable("searchResults", searchResults as java.util.ArrayList<out Parcelable>)

                        val intent = Intent(this, MainActivitySearchResults::class.java)
                        intent.putParcelableArrayListExtra("searchResults", searchResults as java.util.ArrayList<out Parcelable>)
//                        intent.putExtra("extra", extra);
                        startActivity(intent)
                    }
                }
            }
        }

        bindingNewSearchInputBinding.buttonCancel.setOnClickListener{
            setContentView(R.layout.activity_movielist_contents)
            setContentView(background)
        }
    }
}