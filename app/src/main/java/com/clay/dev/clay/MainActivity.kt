package com.clay.dev.clay


import adapter.PageAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics
import android.view.ViewAnimationUtils
import android.view.View
import android.animation.Animator


class MainActivity : AppCompatActivity() {


    private var isOpen: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_anim.setAnimation("user_anim.json")
        mobile_anim.setAnimation("mobile_anim.json")
        fab_add_door.setOnClickListener({

            toggleFab()
        })


        main_toolbar.setTitle(R.string.app_name)
        val fragmentAdapter = PageAdapter(supportFragmentManager, 3)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)
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
