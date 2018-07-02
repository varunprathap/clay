package model

/**
 * Created by cdc_dev on 6/30/18.
 */
class User {

    companion object Factory {
        fun create(): User = User()
    }
    var email: String? = null
    var uuid: String? = null
    var access: Boolean? = null

}