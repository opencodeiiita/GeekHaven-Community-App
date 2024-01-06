package com.example.geekhavencommunityapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.data.Task
import com.example.geekhavencommunityapp.data.myTask
import kotlin.math.floor

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>()
{
    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val taskName: TextView = item.findViewById(R.id.name)
        val taskDescription: TextView = item.findViewById(R.id.des)
        val progressAmount : TextView = item.findViewById(R.id.progressAmount)
        val task_progress: ProgressBar = item.findViewById(R.id.task_progress)
        val image1: ImageView = item.findViewById(R.id.image1) // for putting images of contributors
        val image2: ImageView = item.findViewById(R.id.image2)
        val image3: ImageView = item.findViewById(R.id.image3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item : View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_task_item, parent, false)
        return ViewHolder(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.taskName
        holder.taskDescription.text = task.taskDescription
        holder.progressAmount.text = task.taskProgress.toString() + "/" + task.taskTotal.toString()
        holder.task_progress.setProgress(
            floor(task.taskProgress.toDouble()/task.taskTotal * 100).toInt(), true)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}