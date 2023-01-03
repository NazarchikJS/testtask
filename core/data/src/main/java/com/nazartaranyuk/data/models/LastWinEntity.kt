package com.nazartaranyuk.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "winhistory")
data class LastWinEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val lastWin: Int
)

