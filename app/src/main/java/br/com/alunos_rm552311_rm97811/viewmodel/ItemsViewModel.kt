package br.com.alunos_rm552311_rm97811.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import br.com.alunos_rm552311_rm97811.database.ItemDao
import br.com.alunos_rm552311_rm97811.database.ItemDatabase
import br.com.alunos_rm552311_rm97811.model.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {

    private val itemDao: ItemDao
    val itemsLiveData: LiveData<List<ItemModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        ).build()


        itemDao = database.itemDao()
        itemsLiveData = itemDao.getAll()
    }

    fun addItem(item: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = ItemModel(name = item)
            itemDao.insert(newItem)
        }
    }

    fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }

}