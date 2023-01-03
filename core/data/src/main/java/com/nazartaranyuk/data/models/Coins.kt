package com.nazartaranyuk.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coins")
data class Coins(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val coins: Int
)
