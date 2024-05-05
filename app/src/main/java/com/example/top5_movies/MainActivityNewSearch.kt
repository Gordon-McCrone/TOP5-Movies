package com.example.top5_movies

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.top5_movies.databinding.NewSearchInputBinding
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
import java.net.URLEncoder

class MainActivityNewSearch : AppCompatActivity() {

    lateinit var background: LinearLayout
    var searchResults: MutableList<MovieItemsSearchResults> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_search_input)

        Log.d("*5*", "MainActivityNewSearch onCreate")

        background = LinearLayout(this)

//        bindingNewSearchInputBinding = NewSearchInputBinding.inflate(LayoutInflater.from(this))
//        val view = bindingNewSearchInputBinding.root
//        setContentView(view)

        val newSearchButton = findViewById<Button>(R.id.buttonSearch)
        val newSearchLabel = findViewById<EditText>(R.id.label)

        newSearchButton.setOnClickListener {
            if (newSearchLabel.text.toString().length > 0) {

                runBlocking {
                    GlobalScope.launch {
                        try {
                            val encodedURL = URLEncoder.encode(newSearchLabel.text.toString(), "utf-8")
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
//                        val intent = Intent(this, MainActivitySearchResults::class.java)
//                        intent.putParcelableArrayListExtra("searchResults", searchResults as java.util.ArrayList<out Parcelable>)
//                        startActivity(intent)

                        val intent = Intent(this, MainActivitySearchResults::class.java)
                        intent.putParcelableArrayListExtra("searchResults", searchResults as java.util.ArrayList<out Parcelable>)
                        startForResult.launch(intent)
                    }
                }
            }
        }

        val newSearchButtonCancel = findViewById<Button>(R.id.buttonCancel)
        newSearchButtonCancel.setOnClickListener{
            finish()
        }
    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
//                val intent = result.data
            // Handle the Intent
            //do stuff here
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) return
        data!!.putParcelableArrayListExtra("MovieItemsSearchResults", data!!.getParcelableArrayListExtra<MovieItemsSearchResults>("MovieItemsSearchResults"))

        setResult(Activity.RESULT_OK, data)
        //  as java.util.ArrayList<out Parcelable>
        finish()
    }
}