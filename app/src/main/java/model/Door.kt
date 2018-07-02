package model


class Door {

    companion object Factory {
        fun create(): Door = Door()
    }
    var name: String? = null
    var uuid: String? = null

}