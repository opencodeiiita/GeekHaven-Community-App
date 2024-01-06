package com.example.geekhavencommunityapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.example.geekhavencommunityapp.data.myTask

class myTasksAdapter(private val tasks: List<myTask>) : RecyclerView.Adapter<myTasksAdapter.ViewHolder>()
{
    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val taskName: TextView = item.findViewById(R.id.name)
        val taskDescription: TextView = item.findViewById(R.id.des)
        val taskDate: TextView = item.findViewById(R.id.time)
        val taskProgress: TextView = item.findViewById(R.id.percentage)
        val progress: ProgressBar = item.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val item : View = LayoutInflater.from(parent.context).inflate(R.layout.fragment_mytask_item, parent, false)
        return ViewHolder(item)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position]
        holder.taskName.text = task.taskName
        holder.taskDescription.text = task.taskDescription
        holder.taskDate.text = task.taskDate
        holder.taskProgress.text = task.taskProgress.toString() + "%"
        holder.progress.setProgress(task.taskProgress, true)
    }

    override fun getItemCount(): Int {
        return tasks.size
    }
}