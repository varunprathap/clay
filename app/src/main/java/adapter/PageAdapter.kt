package adapter



import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.clay.dev.clay.DoorFragment
import com.clay.dev.clay.HistoryFragment
import com.clay.dev.clay.UserFragment


/**
 * Created by cdc_dev on 6/30/18.
 */
class PageAdapter (fm: FragmentManager,private var tabCount:Int) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        return when (position) {
            0 -> {
                DoorFragment()
            }
            1 -> UserFragment()as Fragment
            else -> {
                return HistoryFragment()as Fragment
            }
        }
    }

    override fun getCount(): Int {
        return tabCount
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Doors"
            1 -> "Users"
            else -> {
                return "Events"
            }
        }
    }

}