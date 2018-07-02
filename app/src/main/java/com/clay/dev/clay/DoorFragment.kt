package com.clay.dev.clay


import adapter.DoorListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_door.*
import model.DoorItem


/**
 * A simple [Fragment] subclass.
 */
class DoorFragment : Fragment() {

    var user:String?=null
    private lateinit var mDatabase: DatabaseReference
    private lateinit var doorItemList: MutableList<DoorItem>
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
                toogleSkelton()
                if (dataSnapshot.value != null) {
                    doorItemList!!.clear()
                    addDataToList(dataSnapshot.value as Map<String, Any>)
                } else {

                    Toast.makeText(doorView.context, "No door found,Please add using '+' button", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }


        listViewItems.setOnItemClickListener { _, _, position, _ ->

            val selectedDoor = doorItemList[position]
            selectedDoor.position = position
            selectedDoor.user=user

            val detailIntent = DoorDetailActivity.newIntent(doorView.context, selectedDoor)

            startActivity(detailIntent)

        }


        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)
        var skelton_anim = doorView.findViewById<View>(R.id.skelton_anim) as com.airbnb.lottie.LottieAnimationView
        skelton_anim.setAnimation("skelton_anim.json")
        skelton_anim.playAnimation()
        skelton_anim.loop(true)
        skelton_anim.bringToFront()

        return doorView
    }


    private fun addDataToList(doors: Map<String, Any>) {


        //iterate through each user, ignoring their UID
        for ((_, value) in doors) {

            //Get user map
            val singleDoor = value as Map<*, *>
            val doorItem = DoorItem.create()
            doorItem.doorId = singleDoor["uuid"] as String
            doorItem.doorText = singleDoor["name"] as String
            doorItemList!!.add(doorItem);

        }

        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }


    private fun toogleSkelton() {
        skelton_anim.toggleVisibility()
        door_list.bringToFront()
    }

    //toggle visibility
    private fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
        }
    }


    companion object {

        fun newFragment(user:String): Fragment {
            val doorFragment = DoorFragment()
            doorFragment.user=user
            return doorFragment
        }
    }

}
