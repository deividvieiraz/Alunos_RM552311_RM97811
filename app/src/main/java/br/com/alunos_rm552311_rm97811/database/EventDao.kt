package br.com.alunos_rm552311_rm97811.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.alunos_rm552311_rm97811.model.EventModel

@Dao
interface EventDao {
    @Query("SELECT * FROM EventModel")
    fun getAll(): LiveData<List<EventModel>>

    @Insert
    fun insert(event: EventModel)

    @Delete
    fun delete(event: EventModel)
}