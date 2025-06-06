package br.com.alunos_rm552311_rm97811

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.alunos_rm552311_rm97811.model.EventModel
import br.com.alunos_rm552311_rm97811.util.EventsAdapter
import br.com.alunos_rm552311_rm97811.viewmodel.EventsViewModel
import br.com.alunos_rm552311_rm97811.viewmodel.EventsViewModelFactory
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
        supportActionBar?.title = "Painel de Eventos Severos"

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
                val selectedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                eventDate.setText(selectedDate)
            }, year, month, day)

            datePicker.show()
        }

        button.setOnClickListener {

            if (isAnyFieldEmpty(eventLocation, eventType, eventImpactLevel, eventDate, eventAffectedPeopleNumber))
                return@setOnClickListener

            if (Integer.valueOf(eventAffectedPeopleNumber.text.toString()) <= 0) {
                eventAffectedPeopleNumber.error = "Informe valor maior que zero"
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
            hideKeyboard(currentFocus ?: View(this), this)
        }

        val viewModelFactory = EventsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)

        viewModel.eventsLiveData.observe(this) { events ->
            eventsAdapter.updateEvents(events)
        }
    }

    private fun isAnyFieldEmpty(vararg fields: EditText): Boolean {
        var isAnyFieldEmpty = false
        for (field in fields) {
            if (field.text.isEmpty()) {
                field.error = "Preencha um valor"
                isAnyFieldEmpty = true
            } else {
                field.error = null
            }
        }
        return isAnyFieldEmpty
    }

    fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun clearFields() {
        eventLocation.text.clear()
        eventType.text.clear()
        eventImpactLevel.text.clear()
        eventDate.text.clear()
        eventAffectedPeopleNumber.text.clear()
    }

}