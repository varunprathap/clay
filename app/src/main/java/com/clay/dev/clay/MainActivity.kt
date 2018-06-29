package com.clay.dev.clay


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics
import android.view.ViewAnimationUtils
import android.view.View
import android.animation.Animator
import android.annotation.SuppressLint
import android.support.v4.content.ContextCompat


class MainActivity : AppCompatActivity() {



    private var isOpen: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_add_door.setOnClickListener({

            toggleFab()


        })

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setOnClickListener({ toggleFab() })
        toolbar.setTitle(R.string.add_room)
        main_toolbar.setTitle(R.string.app_name)
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

                    add_room_content.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.primary_dark))
                }

                override fun onAnimationEnd(animator: Animator) {

                    add_room_content.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                }

                override fun onAnimationCancel(animator: Animator) {

                }

                override fun onAnimationRepeat(animator: Animator) {

                }
            })
            addRoom.toggleVisibility()
            main_toolbar.title=null
            fab_add_door.toggleVisibility()
            anim.start()
            isOpen = true


        } else {

            val x = addRoom.right.toInt()
            val y = addRoom.bottom.toInt()
            val startRadius = Math.max(layoutContent.width, layoutContent.height).toFloat()
            val endRadius = 0.0f
            val anim = ViewAnimationUtils.createCircularReveal(addRoom, x, y, startRadius, endRadius)
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animator: Animator) {
                    main_toolbar.setTitle(R.string.app_name)
                    add_room_content.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.primary_dark))
                }

                override fun onAnimationEnd(animator: Animator) {
                    addRoom.toggleVisibility()
                    fab_add_door.toggleVisibility()
                    add_room_content.setBackgroundColor(ContextCompat.getColor(this@MainActivity,R.color.white))

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
