package model

/**
 * Created by cdc_dev on 6/30/18.
 */
class History {

    companion object Factory {
        fun create(): History = History()
    }
    var name: String? = null
    var uuid: String? = null

}