package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MonthlyTask : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_monthly_task, container, false)

        val backButton: ImageButton = view.findViewById(R.id.imageButtonBack)
        backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewDates)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)

        val days = generateDays(calendar)
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)

        val adapter = DayAdapter(days) { position ->
            val selectedDate = days[position].first
            updateTaskListText(selectedDate)
        }
        recyclerView.adapter = adapter

        val calendarView: CalendarView = view.findViewById(R.id.simpleCalendarView)
        calendarView.visibility = View.INVISIBLE
        val tasksView: LinearLayout = view.findViewById(R.id.linearLayoutTaskList)
        calendarView.dateTextAppearance = R.style.CalendarDayTextAppearance
        calendarView.weekDayTextAppearance = R.style.CalendarWeekTextAppearance
        calendarView.firstDayOfWeek = Calendar.MONDAY

        val calendarButton: ImageButton = view.findViewById(R.id.imageButtonCalendar)
        calendarButton.setOnClickListener {
            if (calendarView.visibility == View.INVISIBLE) {
                calendarButton.setImageResource(R.drawable.calendar_icon)
                calendarView.visibility = View.VISIBLE
                tasksView.visibility = View.INVISIBLE
            } else {
                calendarButton.setImageResource(R.drawable.calendar_inactive)
                calendarView.visibility = View.INVISIBLE
                tasksView.visibility = View.VISIBLE
            }
        }

        return view
    }

    private fun generateDays(calendar: Calendar): List<Pair<String, String>> {
        val dateList = ArrayList<Pair<String, String>>()

        for (i in 0 until 7) {
            dateList.add(Pair(getFormattedDate(calendar.time), dayOfWeek(calendar.get(Calendar.DAY_OF_WEEK))))
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return dateList
    }

    private fun getFormattedDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        return dateFormat.format(date)
    }

    private fun dayOfWeek(day: Int): String {
        return when (day) {
            Calendar.SUNDAY -> "Sun"
            Calendar.MONDAY -> "Mon"
            Calendar.TUESDAY -> "Tue"
            Calendar.WEDNESDAY -> "Wed"
            Calendar.THURSDAY -> "Thu"
            Calendar.FRIDAY -> "Fri"
            Calendar.SATURDAY -> "Sat"
            else -> "Invalid Day"
        }
    }

    private fun updateTaskListText(selectedDate: String) {
        val textViewDateTaskList: TextView = requireView().findViewById(R.id.textViewDateTaskList)
        textViewDateTaskList.text = "Today's date is $selectedDate"
    }
}

class DayAdapter(private val days: List<Pair<String, String>>, private val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    class DayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDate: TextView = itemView.findViewById(R.id.textViewDate)
        val textViewDay: TextView = itemView.findViewById(R.id.textViewDay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_day, parent, false)
        return DayViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val (date, day) = days[position]
        holder.textViewDate.text = date
        holder.textViewDay.text = day
        if (position == 1) {
            holder.textViewDate.setTextColor(holder.itemView.resources.getColor(R.color.white))
            holder.textViewDay.setTextColor(holder.itemView.resources.getColor(R.color.white))
            holder.itemView.setBackgroundResource(R.drawable.blue_background)
        }

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(position)
        }
    }

    override fun getItemCount(): Int = days.size
}
