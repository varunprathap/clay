package model

/**
 * Created by cdc_dev on 6/30/18.
 */


class Door {

    companion object Factory {
        fun create(): Door = Door()
    }
    var name: String? = null
    var uuid: String? = null

}