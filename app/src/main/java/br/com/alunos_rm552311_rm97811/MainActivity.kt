package br.com.alunos_rm552311_rm97811

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.alunos_rm552311_rm97811.model.EventModel
import br.com.alunos_rm552311_rm97811.viewmodel.EventsViewModelFactory
import br.com.alunos_rm552311_rm97811.util.EventsAdapter
import br.com.alunos_rm552311_rm97811.viewmodel.EventsViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventsViewModel

    lateinit var eventLocation : EditText
    lateinit var eventType : EditText
    lateinit var eventImpactLevel : EditText
    lateinit var eventDate : EditText
    lateinit var eventAffectedPeopleNumber : EditText

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Lista de Eventos"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val eventsAdapter = EventsAdapter { event ->
            viewModel.removeEvent(event)
        }
        recyclerView.adapter = eventsAdapter

        val button = findViewById<Button>(R.id.button)
        eventLocation = findViewById(R.id.eventLocation)
        eventType = findViewById(R.id.eventType)
        eventImpactLevel = findViewById(R.id.eventImpactLevel)
        eventDate = findViewById(R.id.eventDate)
        eventAffectedPeopleNumber = findViewById(R.id.eventAffectedPeopleNumber)


        eventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // Formata a data e coloca no EditText
                val selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                eventDate.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }

        button.setOnClickListener {

            if (isAnyFieldEmpty()) {
                eventLocation.error = "Preencha um valor"
                return@setOnClickListener
            }

            val event = EventModel(
                location = eventLocation.text.toString(),
                type = eventType.text.toString(),
                impactLevel = eventImpactLevel.text.toString(),
                date = eventDate.text.toString(),
                affectedPeopleNumber = Integer.valueOf(eventAffectedPeopleNumber.text.toString())
            )

            viewModel.addEvent(event)
            clearFields()
        }

        val viewModelFactory = EventsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)

        viewModel.eventsLiveData.observe(this) { events ->
            eventsAdapter.updateEvents(events)
        }
    }

    fun isAnyFieldEmpty(): Boolean {
        return eventLocation.text.isEmpty() || eventType.text.isEmpty() ||
                eventImpactLevel.text.isEmpty() || eventDate.text.isEmpty() ||
                eventAffectedPeopleNumber.text.isEmpty()
    }

    fun clearFields() {
        eventLocation.text.clear()
        eventType.text.clear()
        eventImpactLevel.text.clear()
        eventDate.text.clear()
        eventAffectedPeopleNumber.text.clear()
    }

}