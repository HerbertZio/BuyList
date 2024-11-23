package com.zioinnovate.buylist.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Lista(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val itens: String
)