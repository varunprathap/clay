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
 * A simple [Fragment] subclass.
 */
class HistoryFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private var historyItemList: MutableList<History>? = null
    private lateinit var adapter: HistoryListAdapter
    private lateinit var listViewItems: ListView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mDatabase = FirebaseDatabase.getInstance().getReference("history")
        historyItemList = mutableListOf()
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

    private fun addDataToList(doors: Map<String, Any>) {



        //iterate through each user, ignoring their UID
        for ((_, value) in doors) {

            //Get user map
            val singleDoor = value as Map<*, *>
            val historyItem = History.create()

            historyItem.name = singleDoor["name"] as String
            historyItemList!!.add(historyItem);

        }

        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

}// Required empty public constructor
