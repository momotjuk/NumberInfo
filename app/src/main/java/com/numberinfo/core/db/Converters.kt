package com.numberinfo.core.db

fun factToDb(value: String?): Numbers {
    return if (value.isNullOrEmpty()) {
        Numbers()
    } else {
        Numbers(
            null,
            value.substring(0, value.indexOf(" ")),
            value.substring(value.indexOf(" ") + 1, value.length)
        )
    }
}