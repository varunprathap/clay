package com.clay.dev.clay


import adapter.DoorListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.ProgressBar
import com.google.firebase.database.*
import model.DoorItem



/**
 * A simple [Fragment] subclass.
 */
class DoorFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private var doorItemList: MutableList<DoorItem>? = null
    private lateinit var adapter: DoorListAdapter
    private lateinit var listViewItems: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        mDatabase = FirebaseDatabase.getInstance().getReference("doors")
        doorItemList = mutableListOf()
        val doorView = inflater.inflate(R.layout.fragment_door, container, false)
        adapter = DoorListAdapter(this.context!!, doorItemList!!)
        listViewItems = doorView.findViewById<View>(R.id.door_list) as ListView
        listViewItems!!.adapter = adapter


        var itemListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                addDataToList(dataSnapshot.value as Map<String, Any>)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        mDatabase.addValueEventListener(itemListener)


        return doorView
    }

    private fun addDataToList(doors: Map<String, Any>) {

        doorItemList!!.clear()
        //iterate through each user, ignoring their UID
        for ((_, value) in doors) {

            //Get user map
            val singleDoor = value as Map<*, *>
            val doorItem = DoorItem.create()

            doorItem.doorText = singleDoor["name"] as String
            doorItemList!!.add(doorItem);

        }

        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

}
