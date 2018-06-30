package com.clay.dev.clay


import adapter.PageAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics
import android.view.ViewAnimationUtils
import android.view.View
import android.animation.Animator
import android.support.v7.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import model.Door
import model.User
import util.Util


class MainActivity : AppCompatActivity() {


    private var isOpen: Boolean = false
    private lateinit var mAuthenticate: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_anim.setAnimation("user_anim.json")
        mobile_anim.setAnimation("mobile_anim.json")
        fab_add_door.setOnClickListener({

            toggleFab()
        })


        main_toolbar.setTitle(R.string.app_name)
        val fragmentAdapter = PageAdapter(supportFragmentManager, true)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)

        add_user.setOnClickListener({

            val layoutInflater = layoutInflater
            val view = layoutInflater.inflate(R.layout.create_user, null)
            val builder = AlertDialog.Builder(this@MainActivity)

            var email: EditText = view.findViewById<View>(R.id.et_email) as EditText
            var password: EditText = view.findViewById<View>(R.id.et_password) as EditText

            builder.setCancelable(false)
            builder.setTitle("Add User")
            builder.setPositiveButton(R.string.ok, { _, _ ->
                when (Util.validate(email.text.toString(), password.text.toString())) {


                    9999 -> {
                        Toast.makeText(this@MainActivity, "Please enter email or password!", Toast.LENGTH_LONG).show()
                    }

                    9998 -> {
                        Toast.makeText(this@MainActivity, "Password should be at least 6 character!", Toast.LENGTH_LONG).show()
                    }
                    else -> {
                        mAuthenticate = FirebaseAuth.getInstance();
                        mAuthenticate!!.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                                .addOnCompleteListener(this) { task ->
                                    if (task.isSuccessful) {

                                        mDatabase = FirebaseDatabase.getInstance().getReference("users")
                                        val key = mDatabase.push().key
                                        val user = User.create()
                                        user.email = email.text.toString()
                                        user.uuid = key
                                        if (key != null) {
                                            mDatabase.child(key).setValue(user)
                                        }
                                        Toast.makeText(this@MainActivity, "Success, added user", Toast.LENGTH_LONG).show()
                                        toggleFab()

                                    } else {

                                        Toast.makeText(this@MainActivity, "Door creation failed!", Toast.LENGTH_LONG).show()

                                    }
                                }
                    }


                }

            })
            builder.setNegativeButton(R.string.cancel, { _, _ ->
                // do nothing
            })
            builder.setView(view)
            builder.create()
            builder.show()

        })

        add_door.setOnClickListener({

            val layoutInflater = layoutInflater
            val view = layoutInflater.inflate(R.layout.create_door, null)
            val builder = AlertDialog.Builder(this@MainActivity)

            var doorText: EditText = view.findViewById<View>(R.id.et_door_name) as EditText
            builder.setCancelable(false)
            builder.setTitle("Add Door")
            builder.setPositiveButton(R.string.ok, { _, _ ->
                when (Util.isNotNullText(doorText.text.toString())) {

                    true -> {
                        mDatabase = FirebaseDatabase.getInstance().getReference("doors")
                        val key = mDatabase.push().key
                        val door = Door.create()
                        door.name = doorText.text.toString()
                        door.uuid = key
                        if (key != null) {
                            mDatabase.child(key).setValue(door)
                        }

                    }

                    else -> {

                        Toast.makeText(this@MainActivity, "Please enter door name!", Toast.LENGTH_LONG).show()
                    }

                }

            })
            builder.setNegativeButton(R.string.cancel, { _, _ ->
                // do nothing
            })
            builder.setView(view)
            builder.create()
            builder.show()

        })


    }

    private fun toggleFab() {

        if (!isOpen) {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels.toDouble()
            val height = displayMetrics.heightPixels.toDouble()
            val x = layoutContent.right
            val y = layoutContent.bottom
            val startRadius = 0
            val endRadius = Math.hypot(width, height).toInt()
            val anim = ViewAnimationUtils.createCircularReveal(addRoom, x, y, startRadius.toFloat(), endRadius.toFloat())

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    user_anim.playAnimation()
                    mobile_anim.playAnimation()

                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })


            fab_add_door.setImageResource(R.drawable.abc_ic_clear_material)
            addRoom.toggleVisibility()
            anim.start()
            isOpen = true


        } else {

            val x = addRoom.right
            val y = addRoom.bottom
            val startRadius = Math.max(layoutContent.width, layoutContent.height).toFloat()
            val endRadius = 0.0f
            val anim = ViewAnimationUtils.createCircularReveal(addRoom, x, y, startRadius, endRadius)
            fab_add_door.setImageResource(android.R.drawable.ic_input_add)

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {

                }

                override fun onAnimationEnd(animator: Animator) {
                    addRoom.toggleVisibility()
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
            anim.start()
            isOpen = false
        }

    }

    //toggle visibility
    private fun View.toggleVisibility() {
        if (visibility == View.VISIBLE) {
            visibility = View.INVISIBLE
        } else {
            visibility = View.VISIBLE
        }
    }


}
