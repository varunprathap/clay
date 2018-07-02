package com.clay.dev.clay

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_door_detail.*
import java.util.Random
import model.DoorItem
import model.History


class DoorDetailActivity : AppCompatActivity() {


    private var doorId: String = "1"
    private lateinit var mDatabase: DatabaseReference
    private lateinit var title: String
    private lateinit var user: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door_detail)
        //get title from intent
        title = intent.extras.getString(DOOR_NAME)
        // get user from intent
        user = intent.extras.getString(USER)
        detail_toolbar.title = title
        detail_toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        //back button action
        detail_toolbar.setOnClickListener({

            finish()
        })

        //setting button action
        door_settings_button.setOnClickListener({


            var settingIntent = DoorSettings.newIntent(this@DoorDetailActivity, doorId)

            startActivity(settingIntent)


        })

        //load animation
        lock_anim.setAnimation("unlock-blue.json")
        //unlock button action
        unlock_door.setOnClickListener {

            // get a random number between 0 and 10
            val num = rand(0, 10)
            //if even grant access or deny access
            if (num % 2 == 0) {

                //success
                lock_anim.playAnimation()
                lock_anim.addAnimatorListener(object : Animator.AnimatorListener {


                    override fun onAnimationStart(animation: Animator?) {

                    }

                    override fun onAnimationRepeat(animation: Animator?) {

                    }

                    override fun onAnimationCancel(animation: Animator?) {

                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        Toast.makeText(this@DoorDetailActivity, "Access granted for user!", Toast.LENGTH_LONG).show()
                        lock_anim.progress = 0.0f
                        mDatabase = FirebaseDatabase.getInstance().getReference("history")
                        val key = mDatabase.push().key
                        val history = History.create()
                        history.name = "User ${user} has been granted access to ${title}"
                        history.uuid = key
                        history.done = true
                        if (key != null) {
                            mDatabase.child(key).setValue(history)
                        }

                    }

                })


            } else {

                //no access
                val animation = AnimationUtils.loadAnimation(this, R.anim.shake);
                lock_anim.startAnimation(animation)
                animation.duration = 500
                animation.start()

                animation.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationRepeat(animation: Animation?) {

                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        lock_anim.setAnimation("unlock-blue.json")

                    }

                    override fun onAnimationStart(animation: Animation?) {
                        Toast.makeText(this@DoorDetailActivity, "Access denied for user!", Toast.LENGTH_LONG).show()
                        lock_anim.setAnimation("ulock-denied.json")
                        mDatabase = FirebaseDatabase.getInstance().getReference("history")
                        val key = mDatabase.push().key
                        val history = History.create()
                        history.name = "User ${user} has been denied access to ${title}"
                        history.uuid = key
                        history.done = false
                        if (key != null) {
                            mDatabase.child(key).setValue(history)
                        }
                    }

                })

            }


        }


    }

    //to generate random number
    fun rand(from: Int, to: Int): Int {
        val random = Random()
        return random.nextInt(to - from) + from
    }

    companion object {
        private const val DOOR_ID = ""
        private const val DOOR_NAME = ""
        private const val POSITION = ""
        private const val USER = ""

        fun newIntent(context: Context, doorItem: DoorItem): Intent {
            val detailIntent = Intent(context, DoorDetailActivity::class.java)
            detailIntent.putExtra(DOOR_ID, doorItem.doorId)
            detailIntent.putExtra(DOOR_NAME, doorItem.doorText)
            detailIntent.putExtra(POSITION, doorItem.position.toString())
            detailIntent.putExtra(USER, doorItem.user)
            return detailIntent
        }
    }
}
