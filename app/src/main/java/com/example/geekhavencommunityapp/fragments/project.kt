package com.example.geekhavencommunityapp.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Images
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

data class Task(
    val name: String,
    val type: String,
    val progressDone: Int,
    val progressTotal: Int,
)

class project : Fragment() {

    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_project, container, false)

        db = FirebaseFirestore.getInstance()

        val createProjectButton: ImageButton = view.findViewById(R.id.imageButtonAddProject)
        createProjectButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, createProject())
                .addToBackStack(null)
                .commit()
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewTasks)

        val tasks = mutableListOf<Task>()
        db.collection("tasks").get().addOnSuccessListener { result ->
            for (document in result) {
                val name = document.data["name"] as String
                val type = document.data["type"] as String
                val progressDone = document.data["progressDone"] as Long
                val progressTotal = document.data["progressTotal"] as Long
                val task = Task(name, type, progressDone.toInt(), progressTotal.toInt())
                tasks.add(task)
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }


        recyclerView.adapter = TaskAdapter(tasks)

        return view
    }
}

class TaskAdapter(private val tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.textViewTaskTitle)
        val taskType: TextView = view.findViewById(R.id.textViewTaskType)
        val taskProgress: ProgressBar = view.findViewById(R.id.progressBarTask)
        val taskProgressText: TextView = view.findViewById(R.id.textViewTaskProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_task_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.name
        holder.taskType.text = task.type
        holder.taskProgress.progress = task.progressDone * 100 / task.progressTotal
        holder.taskProgressText.text = "${task.progressDone}/${task.progressTotal}"
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}