package model

class User {

    companion object Factory {
        fun create(): User = User()
    }
    var email: String? = null
    var uuid: String? = null
    var access: Boolean? = null

}