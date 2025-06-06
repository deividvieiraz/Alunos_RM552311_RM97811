package br.com.alunos_rm552311_rm97811.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class EventModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val location: String,
    val type: String,
    val impactLevel: String,
    val date: String,
    val affectedPeopleNumber: Int
)
