package model

/**
 * Created by cdc_dev on 6/30/18.
 */
class DoorItem {

    companion object Factory {
        fun create(): DoorItem = DoorItem()
    }

    var doorId: String? = null
    var doorText: String? = null
    var isOpen: Boolean? = false
    var position:Int?=null
    var user: String? = null

}