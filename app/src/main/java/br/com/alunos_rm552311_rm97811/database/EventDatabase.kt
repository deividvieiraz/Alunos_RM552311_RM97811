package br.com.alunos_rm552311_rm97811.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alunos_rm552311_rm97811.model.EventModel

@Database(entities = [EventModel::class], version = 1)
abstract class EventDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}