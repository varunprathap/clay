package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.clay.dev.clay.R
import model.History

class HistoryListAdapter(context: Context, doorItemList: MutableList<History>) : BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList = doorItemList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemText: String = itemList[position].name as String
        val isDone: Boolean = itemList[position].done as Boolean
        val view: View
        val vh: ListRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.history_item, parent, false)
            vh = ListRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ListRowHolder
        }

        vh.label.text = itemText
        //if the history with granted access show unlock image else lock image
        when (isDone) {
            true -> {
                vh.isDone.setImageResource(R.mipmap.ic_lock_open)
            }

            false -> vh.isDone.setImageResource(R.mipmap.ic_lock_outline)
        }

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
        val label: TextView = row!!.findViewById<TextView>(R.id.history_text) as TextView
        val isDone: ImageView = row!!.findViewById<ImageView>(R.id.history_icon) as ImageView
    }
}