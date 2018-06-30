package adapter

import android.content.Context
import com.clay.dev.clay.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import model.DoorItem

class ToDoItemAdapter(context: Context, doorItemList: MutableList<DoorItem>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = doorItemList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val objectId: String = itemList.get(position).doorId as String
        val itemText: String = itemList.get(position).doorText as String
        val done: Boolean = itemList.get(position).isOpen as Boolean

        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.row_item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = itemText
        if (done)vh.isLocked.setImageResource(R.mipmap.ic_lock_outline)
        else vh.isLocked.setImageResource(R.mipmap.ic_lock_open)

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
        val label: TextView = row!!.findViewById<TextView>(R.id.door_text) as TextView
        //val isDone: CheckBox = row!!.findViewById<CheckBox>(R.id.cb_item_is_done) as CheckBox
        val isLocked: ImageButton = row!!.findViewById<ImageButton>(R.id.is_locked) as ImageButton
    }
}