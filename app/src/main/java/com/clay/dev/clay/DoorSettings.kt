package com.clay.dev.clay


import adapter.DoorSettingsViewAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ListView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_door_settings.*
import model.User
import com.google.firebase.FirebaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import android.R.attr.key


class DoorSettings : AppCompatActivity(), ItemListener {

    private lateinit var mDatabase: DatabaseReference
    private lateinit var userItemList: MutableList<User>
    private lateinit var adapter: DoorSettingsViewAdapter
    private lateinit var listViewItems: ListView
    private lateinit var doorId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door_settings)
        //toolbar
        toolbar.setTitle(R.string.title_activity_door_settings)
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setOnClickListener({

            finish()
        })
        //Database reference.
        mDatabase = FirebaseDatabase.getInstance().getReference("users")
        userItemList = mutableListOf()
        //Adapter to hold list view.
        adapter = DoorSettingsViewAdapter(this@DoorSettings!!, userItemList!!)
        listViewItems = findViewById<View>(R.id.door_setting_list) as ListView
        listViewItems!!.adapter = adapter

        var itemListener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.value != null) {
                    userItemList!!.clear()
                    addDataToList(dataSnapshot.value as Map<String, Any>)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }


        doorId = intent.extras.getString(DOOR_ID)
        mDatabase.orderByKey().addListenerForSingleValueEvent(itemListener)


    }

    //interface implementation to save settings when checkbox is changed.
    override fun modifyUserAccess(userId: String, hasAccess: Boolean) {

        mDatabase = FirebaseDatabase.getInstance().getReference("users")
        mDatabase.child(userId).orderByKey().equalTo(doorId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot?.children == null) {
                    mDatabase.child(userId).child(doorId).setValue(true)
                } else {
                    mDatabase.child(userId).child(doorId).setValue(false)
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })


    }

    private fun addDataToList(users: Map<String, Any>) {


        //iterate through each user
        for ((_, value) in users) {

            //Get user map
            val users = value as Map<*, *>
            val userItem = User.create()
            userItem.email = users["email"] as String
            userItem.uuid = users["uuid"] as String
            userItem.access = true

            userItemList!!.add(userItem);

        }

        //alert adapter that has changed
        adapter.notifyDataSetChanged()
    }

    companion object {


        private const val DOOR_ID = ""

        fun newIntent(context: Context, doorId: String): Intent {
            val settingsIntent = Intent(context, DoorSettings::class.java)
            settingsIntent.putExtra(DOOR_ID, doorId)
            return settingsIntent
        }
    }


}
