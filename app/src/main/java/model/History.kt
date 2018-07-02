package model

class History {

    companion object Factory {
        fun create(): History = History()
    }
    var name: String? = null
    var uuid: String? = null
    var done:Boolean?=null

}