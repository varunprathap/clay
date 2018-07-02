package adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.clay.dev.clay.DoorFragment
import com.clay.dev.clay.HistoryFragment
import com.clay.dev.clay.UserFragment


class PageAdapter(fm: FragmentManager, private var isAdmin: Boolean,private  var user:String) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {

        if (isAdmin) {
            return when (position) {
                0 -> DoorFragment.newFragment(user)
                1 -> UserFragment()
                else -> {
                    return HistoryFragment()
                }
            }

        } else {
            return when (position) {
                0 -> DoorFragment.newFragment(user)
                else -> {
                    return HistoryFragment()
                }
            }

        }

    }

    override fun getCount(): Int {


        return when (isAdmin) {
            true -> 3
            false -> 2
        }


    }

    override fun getPageTitle(position: Int): CharSequence {
        if (isAdmin) {
            return when (position) {
                0 -> "Doors"
                1 -> "Users"
                else -> {
                    return "Events"
                }
            }
        } else {
            return when (position) {
                0 -> "Doors"
                else -> {
                    return "Events"

                }
            }
        }
    }

}