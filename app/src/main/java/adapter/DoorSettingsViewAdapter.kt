package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView
import com.clay.dev.clay.ItemListener
import com.clay.dev.clay.R
import model.User

class DoorSettingsViewAdapter (context: Context, doorItemList: MutableList<User>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = doorItemList
    private var rowListener: ItemListener = context as ItemListener


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemText: String = itemList[position].email as String
        val userId: String = itemList[position].uuid as String
        val access:Boolean=itemList[position].access as Boolean
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.door_settings, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = itemText

        vh.checkbox.setOnClickListener {
            rowListener.modifyUserAccess(userId, !access) }
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
        val label: TextView = row!!.findViewById<TextView>(R.id.door_setting_name) as TextView
        val checkbox: CheckBox = row!!.findViewById<TextView>(R.id.cb_door_enabled) as CheckBox
    }
}
