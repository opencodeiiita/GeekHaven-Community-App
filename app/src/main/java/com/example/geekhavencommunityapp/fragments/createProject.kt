package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import com.example.geekhavencommunityapp.R
import com.google.firebase.firestore.FirebaseFirestore

class createProject : Fragment() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_create_project, container, false)

        val backButton: ImageButton = view.findViewById(R.id.imageButtonBack)
        backButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, project())
                .addToBackStack(null)
                .commit()
        }

        val addTaskButton: RelativeLayout = view.findViewById(R.id.addLayout)
        addTaskButton.setOnClickListener { onAddTaskClicked() }

        return view
    }

    fun onAddTaskClicked() {
        val dynamicTaskLayout: LinearLayout = view?.findViewById(R.id.dynamicTaskLayout) ?: return

        // Inflate a new task view
        val newTaskView = layoutInflater.inflate(R.layout.add_task_item, null)

        // Find the views in the new task view
        val editTextTaskName: EditText = newTaskView.findViewById(R.id.editTextTaskName)
        val spinnerTaskType: Spinner = newTaskView.findViewById(R.id.editTextTaskType)
        val btnRemoveTask: ImageButton = newTaskView.findViewById(R.id.imageButtonRemoveTask)

        val taskTypeAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.task_types,
            android.R.layout.simple_spinner_item
        )
        taskTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTaskType.adapter = taskTypeAdapter

        btnRemoveTask.setOnClickListener { onRemoveTaskClicked(newTaskView) }

        // Add the new task view to the dynamic layout
        dynamicTaskLayout.addView(newTaskView)
    }

    fun onRemoveTaskClicked(taskView: View) {
        val dynamicTaskLayout: LinearLayout = view?.findViewById(R.id.dynamicTaskLayout) ?: return

        dynamicTaskLayout.removeView(taskView)
    }
}
