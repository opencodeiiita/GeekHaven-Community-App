package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.google.firebase.firestore.FirebaseFirestore

data class Task(
    val name: String,
    val type: String,
    val progressDone: Int,
    val progressTotal: Int,
)

class project : Fragment() {
    private lateinit var adapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

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

        val searchBox: EditText = view.findViewById(R.id.searchBox)
        searchBox.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProjects(s.toString())
            }
        })

        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewTasks)

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

        adapter = TaskAdapter(tasks)
        recyclerView.adapter = adapter

        return view
    }

    private fun filterProjects(query: String) {
        val filteredList = mutableListOf<Task>()
        for (task in tasks) {
            if (task.name.contains(query, true) || task.type.contains(query, true)) {
                filteredList.add(task)
            }
        }
        adapter.updateList(filteredList)
    }
}

class TaskAdapter(private var tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val taskName: TextView = view.findViewById(R.id.textViewTaskTitle)
        val taskType: TextView = view.findViewById(R.id.textViewTaskType)
        val taskProgress: ProgressBar = view.findViewById(R.id.progressBarTask)
        val taskProgressText: TextView = view.findViewById(R.id.textViewTaskProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_project_item, parent, false)
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

    fun updateList(newList: List<Task>) {
        tasks = newList
        notifyDataSetChanged()
    }
}