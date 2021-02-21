package com.example.kotlindate.andKotlinDateApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.example.kotlindate.shared.Greeting
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindate.andKotlinDateApp.databinding.ActivityMainBinding
import com.example.kotlindate.shared.DataStore
import entity.DateLines
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/*
    ************************************************************************************************
    *   function: greet
    *
    *   Param1: none
    *
    *   Description: This is the default function created by the KMM template. It is used to access
    *                the common shared routine greeting() that is defined in the commonMain module.
    *                It is not used in this module because our DataSource module is placing this in
    *                the output data list. It is left here as a point of reference only
    ************************************************************************************************
 */

fun greet(): String {
    return Greeting().greeting()
}

/*
    ************************************************************************************************
    *   class:      MainActivity
    *   inherits:   AppCompatActivity
    *
    *   Description: This is the main entry point of most Android UI Applications.
    *
    *   This class establishes addressability to the UI and the objects available within it.
    *   By standard android naming conventions a class with the name MainActivity would have a
    *   Layout named activity_main associated with it. When binding is used a class of
    *   ActivityMainBinding is automatically created for the layout activity_main.
    *   By maintaining these naming standards we keep our android code more readable.
    *
    *   The intent of this example is to help the reader make the association between the Android and
    *   iSO environments. When you see a comment with an "iOS" tag I am making a cross reference to a
    *   close iOS equivalent. There are not any exact matching objects but there are matching concepts.
    *
    ************************************************************************************************
 */
class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()                     // Thread manager scope

    internal lateinit var binding: ActivityMainBinding      //  iOS Main StoryBoard Identification
    private lateinit var datesRecyclerView: RecyclerView    //  iOS Table view
    private lateinit var progressBarView: FrameLayout       //  iOS Content view
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout // iOS gesture segues
    private lateinit var dateLinesRVAdaptor: DateLinesRVAdaptor // iOS Table view controller


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   // iOS Create main StoryBoard
        val view = binding.root                             // iOS Set the storyboard to a view object
        setContentView(view)                                // Set the view object as current UI view

        datesRecyclerView = binding.datesListRv             // iOS Set the Cell View
        progressBarView = binding.progressBar
        swipeRefreshLayout = binding.swipeContainer
        //
        // This is where all the work happens. Data is loaded, View Adaptor is loaded and displayed.
        //
        //  Here the displayDates function and the data list is returned and loaded in the display
        //  adaptor or the equivalent the iOS table view adaptor
        //
        dateLinesRVAdaptor = DateLinesRVAdaptor(displayDates(false))
        datesRecyclerView.adapter = dateLinesRVAdaptor
        datesRecyclerView.layoutManager = LinearLayoutManager(this)

        // iOS segue handler code to process gesture
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            displayDates(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
    /*
        ************************************************************************************************
        *   function: displayDates
        *
        *   Param1: needReload  Boolean flag to force a data reload
        *
        *   return: A list of DateLines
        *
        *   Description: This is the function where the data is retrieved form the common source.
        *                The required UI handling is included here for No Data and Error Handling.
        *                The data is retrieved via a coroutine launch that is monitored for success
        *                When successful the data is returned to the adaptor
        ************************************************************************************************
     */
    private fun displayDates(needReload: Boolean):List<DateLines> {
        progressBarView.visibility = View.VISIBLE
        val ctx = this.baseContext
        val ds = DataStore()
        var ret = listOf(DateLines("Initial", "Value"))
        mainScope.launch {
            kotlin.runCatching {
                ds.getStoredData()
            }.onSuccess { it ->

                dateLinesRVAdaptor.dateLines = it
                dateLinesRVAdaptor.notifyDataSetChanged()
                ret = it

            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                dateLinesRVAdaptor = DateLinesRVAdaptor(listOf(DateLines("Error", "Occurred")))
            }
            progressBarView.visibility = View.INVISIBLE
            datesRecyclerView.adapter = dateLinesRVAdaptor
            datesRecyclerView.layoutManager = LinearLayoutManager(ctx)
        }
        return ret
    }
}