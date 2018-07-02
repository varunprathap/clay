package com.clay.dev.clay

/**
 * Interface for settings item listener events.
 */
interface ItemListener{

    fun modifyUserAccess(userId: String, hasAccess: Boolean)
}