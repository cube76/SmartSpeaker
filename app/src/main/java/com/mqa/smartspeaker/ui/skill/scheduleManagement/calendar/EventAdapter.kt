package com.mqa.smartspeaker.ui.skill.scheduleManagement.calendar

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mqa.smartspeaker.core.domain.model.Event
import com.mqa.smartspeaker.core.utils.layoutInflater
import com.mqa.smartspeaker.databinding.ItemEventBinding

class EventAdapter (val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<EventAdapter.Example3EventsViewHolder>() {

    val events = mutableListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example3EventsViewHolder {
        return Example3EventsViewHolder(
            ItemEventBinding.inflate(parent.context.layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: Example3EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class Example3EventsViewHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onClick(events[bindingAdapterPosition])
            }
        }

        fun bind(event: Event) {
            binding.itemEventText.text = event.text
        }
    }
}