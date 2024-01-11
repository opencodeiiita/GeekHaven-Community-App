package com.example.geekhavencommunityapp.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import app.futured.donut.DonutProgressView
import app.futured.donut.DonutSection
import com.example.geekhavencommunityapp.R

class ProjectDetails(
    private var todoAmount: Int,
    private var inProgressAmount: Int,
    private var doneAmount: Int
) : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_project_details, container, false)

        val backButton: ImageButton = view.findViewById(R.id.imageButtonBack)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, project())
                .addToBackStack(null)
                .commit()
        }

        val completedPercentText: TextView = view.findViewById(R.id.textViewProgress)
        completedPercentText.text = "${doneAmount * 100 / (todoAmount + inProgressAmount + doneAmount)}%"

        val completedText: TextView = view.findViewById(R.id.textViewTaskCompletedCount)
        completedText.text = "$doneAmount Task done"

        val todoText: TextView = view.findViewById(R.id.textViewTaskToDoCount)
        todoText.text = "$todoAmount Task to do"

        val inProgressText: TextView = view.findViewById(R.id.textViewTaskOngoingCount)
        inProgressText.text = "$inProgressAmount Task in progress"

        val donutGraph: DonutProgressView = view.findViewById(R.id.donut_progress)
        val todoSection = DonutSection(
            name = "Todo",
            color = Color.parseColor("#b1d199"),
            amount = todoAmount.toFloat()
        )
        val inProgressSection = DonutSection(
            name = "In Progress",
            color = Color.parseColor("#ffb35a"),
            amount = inProgressAmount.toFloat()
        )
        val completedSection = DonutSection(
            name = "Done",
            color = Color.parseColor("#3580ff"),
            amount = doneAmount.toFloat()
        )
        donutGraph.cap = (todoAmount + inProgressAmount + doneAmount).toFloat()
        donutGraph.submitData(listOf(todoSection, inProgressSection, completedSection))

        return view
    }
}