package br.com.alunos_rm552311_rm97811

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import br.com.alunos_rm552311_rm97811.viewmodel.EventsViewModelFactory
import br.com.alunos_rm552311_rm97811.util.EventsAdapter
import br.com.alunos_rm552311_rm97811.viewmodel.EventsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: EventsViewModel

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
        val editText = findViewById<EditText>(R.id.editText)

        button.setOnClickListener {
            if (editText.text.isEmpty()) {
                editText.error = "Preencha um valor"
                return@setOnClickListener
            }

            viewModel.addEvent(editText.text.toString())
            editText.text.clear()
        }

        val viewModelFactory = EventsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(EventsViewModel::class.java)

        viewModel.eventsLiveData.observe(this) { events ->
            eventsAdapter.updateEvents(events)
        }
    }

}