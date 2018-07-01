package com.clay.dev.clay

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.PorterDuffXfermode
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_door_detail.*
import model.DoorItem
import com.airbnb.lottie.LottieAnimationView



class DoorDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_door_detail)
        val title = intent.extras.getString(DOOR_NAME)
        detail_toolbar.title=title
        detail_toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        detail_toolbar.setOnClickListener({

            finish()
        })

        lock_anim.setAnimation("unlock-blue.json")
        unlock_door.setOnClickListener {

            val statusView = this.findViewById<LottieAnimationView>(R.id.lock_anim) as LottieAnimationView
            statusView.colorFilter=PorterDuffColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP)
            //lock_anim.colorFilter = PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SCREEN)
            lock_anim.playAnimation()



        }
    }

    companion object {
        private const val DOOR_ID = ""
        private const val DOOR_NAME = ""

        fun newIntent(context: Context, doorItem: DoorItem): Intent {
            val detailIntent = Intent(context, DoorDetailActivity::class.java)
            detailIntent.putExtra(DOOR_ID, doorItem.doorId)
            detailIntent.putExtra(DOOR_NAME, doorItem.doorText)
            return detailIntent
        }
    }
}
