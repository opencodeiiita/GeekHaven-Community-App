package com.example.geekhavencommunityapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.adapters.TaskAdapter
import com.example.geekhavencommunityapp.adapters.myTasksAdapter
import com.example.geekhavencommunityapp.data.Task
import com.example.geekhavencommunityapp.data.myTask
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.Date

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // setting date
        val date = Date()
        val dateFormat = SimpleDateFormat("EEEE, dd")
        val format = dateFormat.format(date)

        val textView = view.findViewById<TextView>(R.id.textView1)
        textView.text = format


        // setting mytasks
        fillMyTasks(view)

        // setting tasks
        fillTasks(view)


        return view
    }


    private fun fillMyTasks(view:View)
    {
        val myTasksDb = Firebase.firestore.collection("myTask")
        val myTasks = mutableListOf<myTask>()

        myTasksDb.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val myTaskData = document.data
                    val myTaskEntry = myTask(
                        myTaskData["name"] as String,
                        myTaskData["description"] as String,
                        myTaskData["date"] as String,
                        myTaskData["progress"] as Long,
                    )
                    myTasks.add(myTaskEntry)


                }

                val myTaskRecycler: RecyclerView = view.findViewById(R.id.myTasks)
                val adapter = myTasksAdapter(myTasks)

                myTaskRecycler.adapter = adapter
                myTaskRecycler.layoutManager = LinearLayoutManager(requireContext())

            }
            .addOnFailureListener{
                fillMyTasks(view)
            }
    }
    private fun fillTasks(view: View)
    {
        val tasksDb = Firebase.firestore.collection("Task")
        val tasks = mutableListOf<Task>()

        tasksDb.get()
            .addOnSuccessListener{
                    documents->
                for(document in documents) {
                    val taskData = document.data
                    val taskEntry = Task(
                        taskData["name"] as String,
                        taskData["description"] as String,
                        taskData["progress"] as Long,
                        taskData["total"] as Long,
                        taskData["image1"] as String,
                        taskData["image2"] as String,
                        taskData["image3"] as String,
                    )
                    tasks.add(taskEntry)
                }

                val TaskRecycler: RecyclerView = view.findViewById(R.id.tasks)
                val Taskadapter = TaskAdapter(tasks)

                TaskRecycler.adapter = Taskadapter
                TaskRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            .addOnFailureListener{
                fillTasks(view)
            }


    }

}