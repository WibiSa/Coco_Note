package me.wibisa.coconote.core.util

import java.util.UUID

object Util {
    fun generateUniqueId(): String {
        return UUID.randomUUID().toString()
    }
}