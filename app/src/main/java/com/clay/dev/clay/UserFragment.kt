package com.clay.dev.clay


import adapter.UserListAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.google.firebase.database.*
import model.User


class UserFragment : Fragment() {

    private lateinit var mDatabase: DatabaseReference
    private var userItemList: MutableList<User>? = null
    private lateinit var adapter: UserListAdapter
    private lateinit var listViewItems: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //get database reference
        mDatabase = FirebaseDatabase.getInstance().getReference("users")
        //user list
        userItemList = mutableListOf()
        // Inflate the layout for this fragment
        val userView = inflater.inflate(R.layout.fragment_user, container, false)
        //adapter to hold data
        adapter = UserListAdapter(this.context!!, userItemList!!)
        listViewItems = userView.findViewById<View>(R.id.user_list) as ListView
        listViewItems!!.adapter = adapter


        var itemListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.value != null) addDataToList(dataSnapshot.value as Map<String, Any>)
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        mDatabase.addValueEventListener(itemListener)
        return userView
    }

    private fun addDataToList(users: Map<String, Any>) {

        userItemList!!.clear()
        //iterate through each user
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

}
