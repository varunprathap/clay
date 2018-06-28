package com.clay.dev.clay

import android.content.Context
import android.animation.Animator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import android.app.Activity

import android.os.Handler

import android.view.View


/**
 * A login screen that offers login via userid/password
 */
class LoginActivity : AppCompatActivity() {


    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuthenticate: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        lock_animation.setAnimation("unlock.json")

        activity_indicator.setAnimation("loading.json")
        activity_indicator.playAnimation()
        activity_indicator.loop(true)
        activity_indicator.toggleVisibility()



        btn_login.setOnClickListener {

            //to hide keyboard
            hideKeyboard(btn_login)
            //activity indicator toggle
            activity_indicator.toggleVisibility()
            // validate if there is email and password or alert user.
            if (validate()) {
                //delayed a bit for progress.
                val handler = Handler()
                handler.postDelayed({ login(input_email.text.toString().trim().toLowerCase(), input_password.text.toString().trim().toLowerCase()) }, 3000)
            }
        }
    }

    private fun validate(): Boolean {


        var valid = true

        val email = input_email.text
        val password = input_password.text

        if (email.isEmpty()) {
            input_email?.error = "please enter your name"
            valid = false
        }


        if (password.isEmpty()) {
            input_password?.error = "Please enter password"
            valid = false
        }

        return valid
    }


    private fun authenticate(): Boolean {


        val authenicated = true

        //connect to firebase realtime database
        mDatabase = FirebaseDatabase.getInstance().getReference("Users")

        val user = mDatabase.child(input_email.text.toString().trim().toLowerCase())

        return authenicated


    }

    private fun login(email: String, password: String) {

        mAuthenticate = FirebaseAuth.getInstance();
        mAuthenticate!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    //hide activity indicator
                    activity_indicator.toggleVisibility()

                    if (task.isSuccessful) {

                        lock_animation.playAnimation()
                        lock_animation.addAnimatorListener(object : Animator.AnimatorListener {


                            override fun onAnimationStart(animation: Animator?) {

                            }

                            override fun onAnimationRepeat(animation: Animator?) {

                            }

                            override fun onAnimationCancel(animation: Animator?) {

                            }

                            override fun onAnimationEnd(animation: Animator?) {
                                Toast.makeText(this@LoginActivity, "Logged in!", Toast.LENGTH_SHORT).show()
                            }


                        })

                    } else {

                        Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()

                    }

                    if (!task.isSuccessful) {
                        Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    }
                }
    }


    //hide keyboard
    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
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
