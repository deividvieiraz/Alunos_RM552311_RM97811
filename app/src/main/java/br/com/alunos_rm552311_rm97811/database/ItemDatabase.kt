package br.com.alunos_rm552311_rm97811.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alunos_rm552311_rm97811.model.ItemModel

@Database(entities = [ItemModel::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}