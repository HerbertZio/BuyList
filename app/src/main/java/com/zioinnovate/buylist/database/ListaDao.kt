package com.zioinnovate.buylist.database

import androidx.room.*

@Dao
interface ListaDao {
    @Query("SELECT nome FROM Lista")
    fun getAllListNames(): List<String>

    @Query("SELECT * FROM Lista WHERE nome = :nome")
    fun getByName(nome: String): Lista?

    @Insert
    fun insert(lista: Lista)

    @Update
    fun update(lista: Lista)

    @Query("DELETE FROM Lista WHERE nome = :nome")
    fun deleteByName(nome: String)  // Exclusão de lista pelo nome
}
