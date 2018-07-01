package com.clay.dev.clay


import adapter.DoorListAdapter
import adapter.UserListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*
import model.DoorItem
import model.User


/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private var userItemList: MutableList<User>? = null
    private lateinit var adapter: UserListAdapter
    private lateinit var listViewItems: ListView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mDatabase = FirebaseDatabase.getInstance().getReference("users")
        userItemList = mutableListOf()
        val userView = inflater.inflate(R.layout.fragment_user, container, false)
        adapter = UserListAdapter(this.context!!, userItemList!!)
        listViewItems = userView.findViewById<View>(R.id.user_list) as ListView
        listViewItems!!.adapter = adapter


        var itemListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI

                addDataToList(dataSnapshot.value as Map<String, Any>)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        mDatabase.addValueEventListener(itemListener)
        return userView
    }

    private fun addDataToList(users: Map<String, Any>) {

        userItemList!!.clear()
        //iterate through each user, ignoring their UID
        for ((_, value) in users) {

            //Get user map
            val users = value as Map<*, *>
            val userItem = User.create()



            userItem.email = users["email"] as String

            userItemList!!.add(userItem);


        }

        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

}// Required empty public constructor
