package com.example.geekhavencommunityapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

data class User(val id: String, val name: String)


class community : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        // Initialize RecyclerView and Adapter
        recyclerView = view.findViewById(R.id.recyclerViewUsers)
        userAdapter = UserAdapter()

        // Set up RecyclerView with LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = userAdapter

        // Fetch user data from Firestore
        fetchUsers()

        return view
    }

    private fun fetchUsers() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val userList = mutableListOf<User>()

                for (document in result) {
                    val name = document.getString("name")
                    if (name != null) {
                        val user = User(document.id, name)
                        userList.add(user)
                    }
                }

                // Update RecyclerView with fetched user data
                userAdapter.setUserList(userList)
            }
            .addOnFailureListener { exception ->
                // Handle errors
            }
    }
}

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var userList: List<User> = listOf()

    fun setUserList(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]

        val otherUserId = user.id
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid.toString()

        // Bind user data to ViewHolder
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val roomId = createRoomIfNotExist(currentUserId, otherUserId)
            val chatFragment = Chat(roomId, otherUserId)
            val transaction = it.context as androidx.fragment.app.FragmentActivity
            transaction.supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, chatFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userNameTextView: TextView = itemView.findViewById(R.id.textViewSenderName)

        fun bind(user: User) {
            // Bind user data to UI components
            userNameTextView.text = user.name
        }
    }

    fun getRoomId(currentUserId: String, otherUserId: String): String {
        val roomIds = mutableListOf<String>()
        roomIds.add(currentUserId + otherUserId)
        roomIds.add(otherUserId + currentUserId)
        roomIds.sort()
        return roomIds[0]
    }

    fun createRoomIfNotExist(currentUserId: String, otherUserId: String): String {
        val roomId = getRoomId(currentUserId, otherUserId)
        val realtimeDB = FirebaseDatabase.getInstance()

        realtimeDB.getReference("rooms/$roomId").get().addOnSuccessListener {
            if (!it.exists()) {
                realtimeDB.getReference("rooms/$roomId").setValue(hashMapOf(
                    "messages" to listOf<String>()
                ))
            }
        }

        return roomId
    }
}

