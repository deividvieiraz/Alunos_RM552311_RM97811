package br.com.alunos_rm552311_rm97811.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import br.com.alunos_rm552311_rm97811.database.EventDao
import br.com.alunos_rm552311_rm97811.database.EventDatabase
import br.com.alunos_rm552311_rm97811.model.EventModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventsViewModel(application: Application) : AndroidViewModel(application) {

    private val eventDao: EventDao
    val eventsLiveData: LiveData<List<EventModel>>

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            EventDatabase::class.java,
            "events_database"
        ).build()

        eventDao = database.eventDao()
        eventsLiveData = eventDao.getAll()
    }

    fun addEvent(event: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newEvent = EventModel(name = event)
            eventDao.insert(newEvent)
        }
    }

    fun removeEvent(event: EventModel) {
        viewModelScope.launch(Dispatchers.IO) {
            eventDao.delete(event)
        }
    }

}