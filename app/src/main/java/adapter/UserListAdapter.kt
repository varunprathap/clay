package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.clay.dev.clay.R
import model.DoorItem
import model.User

class UserListAdapter(context: Context, doorItemList: MutableList<User>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = doorItemList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemText: String = itemList[position].email as String
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.user_item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = itemText
        vh.userName.text=getUsernameFromEmail(itemText)

        //if (done)vh.isLocked.setImageResource(R.mipmap.ic_lock_outline)
        //else vh.isLocked.setImageResource(R.mipmap.ic_lock_open)

        return view
    }

    override fun getItem(index: Int): Any {
        return itemList.get(index)
    }

    override fun getItemId(index: Int): Long {
        return index.toLong()
    }

    override fun getCount(): Int {
        return itemList.size
    }

    private class ListRowHolder(row: View?) {
        val label: TextView = row!!.findViewById<TextView>(R.id.user_email_text) as TextView
        val userName: TextView = row!!.findViewById<TextView>(R.id.user_name_text) as TextView
    }
    private fun getUsernameFromEmail(email: String?): String {
        return if (email!!.contains("@")) {
            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        } else {
            email
        }
    }
}