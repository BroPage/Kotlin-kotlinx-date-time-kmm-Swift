package com.example.kotlindate.andKotlinDateApp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindate.andKotlinDateApp.databinding.ActivityMainBinding
import com.example.kotlindate.andKotlinDateApp.databinding.ItemDateBinding
import entity.DateLines

/*  ************************************************************************************************
    *
    *  class:    DateLinesRVAdaptor extends RecyclerView.Adapter
    *  passing:     inner class DateViewHolder
    *
    *  Param1:      dateLines as a list of DateLines defined in the entity class file
    *
    *  return:
    *
    *  Description: This class establishes the data connection between the data object passed in
    *               Param1 and the on layout view item that will display the list in the UI
    *
    *               iOS Table View controller like class
    *
    ************************************************************************************************
 */

class DateLinesRVAdaptor(var dateLines: List<DateLines>) : RecyclerView.Adapter<DateLinesRVAdaptor.DateViewHolder>() {
    private lateinit var binding: ItemDateBinding
    //          fun onCreateViewHolder
    //  Called at creation this function receives a pointer to the view item on the UI with a type
    //  it will use this information to inflate the view with the detail line item layout and tell
    //  the system where to go to process each individual item in the list
    //
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {

        //  iOS setup output to cell item fields through the current view item or recycler item
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_date, parent, false)
            .run(::DateViewHolder)
    }
    //              fun getItemCount()
    //  This function returns the count of items that are found in the passed list
    //
    override fun getItemCount(): Int = dateLines.count()

    //              fun onBindViewHolder
    //  This Function is called for each item in the list. Here we can access each list item and
    //  perform processing on them. For our design porpoise we process the line item at this level
    //  then we pass the detailed data to the view holder for field processing
    //
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        holder.bindData(dateLines[position])
    }

    //
    //  This function is the detailed data item processing module.
    //  It first establishes addressability to the view items on the detail layout at creation time.
    //  The the bindData function is called for each items in the list to have the data properly
    //  placed on the detail layout.
    //
    //  iOS Cell View Controller like class
    //
    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // iOS actual view cell controller address for each field

        private val dateNameTextView = itemView.findViewById<TextView>(R.id.dateName)
        private val dateValueTextView = itemView.findViewById<TextView>(R.id.dateValue)

        //  iOS Place each detail item on the layout of the current RecyclerView or cell item
        fun bindData(date: DateLines) {
            dateNameTextView.text =  date.dateName
            dateValueTextView.text =  date.dateValue
        }
    }
}