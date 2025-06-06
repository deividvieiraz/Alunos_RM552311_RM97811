package br.com.alunos_rm552311_rm97811.util

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import br.com.alunos_rm552311_rm97811.R
import br.com.alunos_rm552311_rm97811.model.EventModel

class EventsAdapter(private val onEventRemoved: (EventModel) -> Unit) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    private var events = listOf<EventModel>()

    inner class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val eventLocation = view.findViewById<TextView>(R.id.viewLocation)
        val eventType = view.findViewById<TextView>(R.id.viewType)
        val eventImpact = view.findViewById<TextView>(R.id.viewImpact)
        val eventDate = view.findViewById<TextView>(R.id.viewDate)
        val eventAffected = view.findViewById<TextView>(R.id.viewAffected)
        val button = view.findViewById<ImageButton>(R.id.imageButtonDelete)

        fun bind(event: EventModel) {
            eventLocation.text = event.location
            eventType.text = event.type
            eventImpact.text = event.impactLevel
            eventDate.text = event.date
            eventAffected.text = event.affectedPeopleNumber.toString()

            button.setOnClickListener {
                onEventRemoved(event)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.event_layout, parent, false)
        return EventViewHolder(view)
    }

    override fun getItemCount(): Int = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
    }

    fun updateEvents(newEvents: List<EventModel>) {
        events = newEvents
        notifyDataSetChanged()
    }

}