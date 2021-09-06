package com.mqa.smartspeaker.ui.skill.scheduleManagement.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mqa.smartspeaker.databinding.FrameAddScheduleBinding
import java.util.*

class AddEventFragmentDialog : BottomSheetDialogFragment(){
    private lateinit var _binding: FrameAddScheduleBinding
    private val binding get() = _binding

//    private val events = mutableMapOf<LocalDate, List<Event>>()
//    var formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    val selectedDate: LocalDate = LocalDate.parse(Prefs.getString("selected_day",""), formatter)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FrameAddScheduleBinding.inflate(inflater, container, false)

        binding.btnSaveEvent.setOnClickListener {
//            saveEvent(editText.text.toString())
//            // Prepare EditText for reuse.
//            editText.setText("")
        }

        return binding.root
    }

//    fun saveEvent(text: String) {
//        if (text.isBlank()) {
//            Toast.makeText(requireContext(), "input event", Toast.LENGTH_LONG).show()
//        } else {
//            selectedDate?.let {
//                events[it] =
//                    events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), text, it))
//                updateAdapterForDate(it)
//            }
//        }
//    }
//
//    private fun deleteEvent(event: Event) {
//        val date = event.date
//        events[date] = events[date].orEmpty().minus(event)
//        updateAdapterForDate(date)
//    }
//
//    fun updateAdapterForDate(date: LocalDate) {
//        eventsAdapter.apply {
//            events.clear()
//            events.addAll(this@CalendarFragment.events[date].orEmpty())
//            notifyDataSetChanged()
//        }
////        binding.textView62.text = selectionFormatter.format(date)
//    }
}