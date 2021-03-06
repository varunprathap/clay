package com.clay.dev.clay


import adapter.DoorListAdapter
import adapter.HistoryListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*
import model.DoorItem

import model.History


/**
 * History fragment
 */
class HistoryFragment : Fragment() {

    //database reference.
    private lateinit var mDatabase: DatabaseReference
    private var historyItemList: MutableList<History>? = null
    //adapter
    private lateinit var adapter: HistoryListAdapter
    private lateinit var listViewItems: ListView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mDatabase = FirebaseDatabase.getInstance().getReference("history")
        historyItemList = mutableListOf()
        // Inflate the layout for this fragment
        val historyView = inflater.inflate(R.layout.fragment_history, container, false)
        adapter = HistoryListAdapter(this.context!!, historyItemList!!)
        listViewItems = historyView.findViewById<View>(R.id.history_list) as ListView
        listViewItems!!.adapter = adapter


        var itemListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if(dataSnapshot.value!=null)addDataToList(dataSnapshot.value as Map<String, Any>)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)
        return historyView
    }

    private fun addDataToList(history: Map<String, Any>) {

        //iterate through each history, ignoring their UID
        for ((_, value) in history) {

            //Get user map
            val singleHistory = value as Map<*, *>
            val historyItem = History.create()

            historyItem.name = singleHistory["name"] as String
            historyItem.done=singleHistory["done"] as Boolean

            historyItemList!!.add(historyItem);

        }

        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

}
