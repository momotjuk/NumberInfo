package com.numberinfo.core.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "numbers_table")
data class Numbers(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "number") val number: String = "0",
    @ColumnInfo(name = "fact") val fact: String = "Nothing"
) : Serializable
