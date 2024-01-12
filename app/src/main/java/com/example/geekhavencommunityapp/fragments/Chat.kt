package com.example.geekhavencommunityapp.fragments
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.IBinder.DeathRecipient
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geekhavencommunityapp.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class ChatMessage(
    val message: String,
    val sender: String,
    val date: Date,
)

class Chat(roomId: String, recipientId: String) : Fragment() {

    private lateinit var realtimeDB: DatabaseReference
    private lateinit var firestore: FirebaseFirestore
    private val roomId = roomId
    private val recipientId = recipientId

    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_chat, container, false)

        val backBtn: ImageView = view.findViewById(R.id.back)
        backBtn.setOnClickListener {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.nav_host_fragment, community())
            transaction.commit()
        }

        realtimeDB = FirebaseDatabase.getInstance().reference
        firestore = FirebaseFirestore.getInstance()

        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        val name: TextView = view.findViewById(R.id.textView10)

        firestore.collection("users").document(recipientId).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    name.setText(document.data?.get("name").toString())
                }
            }

        // Example messageList
//        val dateFormat = SimpleDateFormat("MMMM dd, yyyy hh:mm a", Locale.US)
//        val date1 = dateFormat.parse("January 01, 2024 10:59 PM")
//        val date2 = dateFormat.parse("January 02, 2024 11:15 AM")
//        val date3 = dateFormat.parse("January 03, 2024 02:30 PM")
//        val date4 = dateFormat.parse("January 04, 2024 02:30 PM")
//
//        val messageList = mutableListOf<ChatMessage>(
//            ChatMessage("Hello", currentUser!!, date2!!),
//            ChatMessage("Hi there", recipientId, date1!!),
//            ChatMessage("How are you?", currentUser, date1),
//            ChatMessage("I'm good, thanks!", recipientId, date3!!),
//            ChatMessage("What's up?", currentUser, date2),
//            ChatMessage("Not much", recipientId, date4!!),
//            ChatMessage(
//                "Like I had a big confusion, can you help me sort out?",
//                currentUser,
//                date4
//            ),
//            ChatMessage(
//                "I am not sure, I can try. I would recommend going to muffinBoy. That guy is great. Like I am trying to make this message long for testing and I don't know what else to type?",
//                currentUser,
//                date4
//            ),
//        )


        val messageList = mutableListOf<ChatMessage>()

        // send new message list to adapter.updateMessageList if data change
//        realtimeDB.child("rooms/$roomId").child("messages").addValueEventListener(object :
//            ValueEventListener {
//            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
//                val messages = snapshot.value as List<HashMap<String, Any>>
//                val newMessageList = mutableListOf<ChatMessage>()
//                for (message in messages) {
//                    val messageText = message["message"] as String
//                    val sender = message["sender"] as String
//                    val date = message["date"] as Long
//                    val chatMessage = ChatMessage(messageText, sender, Date(date))
//                    newMessageList.add(chatMessage)
//                }
//                val adapter = view.findViewById<RecyclerView>(R.id.messageRecyclerView).adapter as ChatAdapter
//                adapter.updateMessageList(newMessageList)
//            }
//
//            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
//        })


        val recycler = view.findViewById<RecyclerView>(R.id.messageRecyclerView)
        val adapter = ChatAdapter(messageList)


        val messageBox = view.findViewById<EditText>(R.id.messageBox)
        val send = view.findViewById<ImageView>(R.id.imageView9)


        messageBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (messageBox.text.toString().trim().isNotEmpty()) {
                    send.setImageResource(R.drawable.send)
                } else {
                    send.setImageResource(R.drawable.microphone)
                }
            }

            override fun afterTextChanged(s: Editable?) {
                if (messageBox.text.toString().trim().isNotEmpty()) {
                    send.setImageResource(R.drawable.send)
                } else {
                    send.setImageResource(R.drawable.microphone)
                }
            }
        })

        send.setOnClickListener {
            if (messageBox.text.toString().trim().isNotEmpty()) {
                val message = ChatMessage(messageBox.text.toString(), currentUser!!, Date())
                adapter.addMessage(roomId, message)
                messageBox.text.clear()
            }
        }
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        realtimeDB.child("rooms/$roomId").child("messages").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                adapter.updateMessageList(roomId)
            }
            override fun onCancelled(error: com.google.firebase.database.DatabaseError) {}
        })

        return view
    }
}

val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.US)
val timeFormat = SimpleDateFormat("hh:mm a", Locale.US)

class ChatAdapter(private val Chats: MutableList<ChatMessage>):
    RecyclerView.Adapter<ChatAdapter.ViewHolder>()
{

    inner class ViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val date : TextView = item.findViewById(R.id.messageDate)
        val message : TextView = item.findViewById(R.id.messageContent)
        val time : TextView = item.findViewById(R.id.messageTime)
    }

    override fun getItemViewType(position: Int): Int {
        val currentUser = FirebaseAuth.getInstance().currentUser?.uid
        if(Chats[position].sender == currentUser)
        {
            return 0
        } else {
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item : View  = LayoutInflater.from(parent.context).inflate(R.layout.fragment_chat_item_me, parent, false)
        if(viewType == 1)
        {
            item = LayoutInflater.from(parent.context).inflate(R.layout.fragment_chat_item_them, parent, false)
        }
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cur_message = Chats[position]
        val today = Date()

        // Calculate yesterday's date in milliseconds (86400000 milliseconds in a day)
        val yesterdayTimeMillis = today.time - 86400000

        // Create a Date object for yesterday
        val yesterday = Date(yesterdayTimeMillis)

        // Get the date strings for today and yesterday
        val todayDateString = dateFormat.format(today)
        val yesterdayDateString = dateFormat.format(yesterday)

        if(dateFormat.format(cur_message.date) == todayDateString)
        {
            holder.date.text = "Today"
        }
        else if(dateFormat.format(cur_message.date) == yesterdayDateString)
        {
            holder.date.text = "Yesterday"
        }
        else
        {
            holder.date.text = dateFormat.format(cur_message.date)
        }

        holder.message.text = cur_message.message
        holder.time.text = timeFormat.format(cur_message.date)

        if(position != 0 && dateFormat.format(Chats[position - 1].date) == dateFormat.format(Chats[position].date))
        {
            holder.date.visibility = View.GONE
        }
        else
        {
            holder.date.visibility = View.VISIBLE
        }

    }

    override fun getItemCount(): Int {
        return Chats.size
    }

    fun addMessage(roomId: String, message: ChatMessage) {
        val realtimeDB: DatabaseReference = FirebaseDatabase.getInstance().reference
        realtimeDB.child("rooms").child(roomId).child("messages").push().setValue(message).addOnSuccessListener {
            updateMessageList(roomId)
        }
    }

    fun updateMessageList(roomId: String) {
        Chats.clear()
        val realtimeDB: DatabaseReference = FirebaseDatabase.getInstance().reference
        realtimeDB.child("rooms/$roomId").child("messages").get().addOnSuccessListener { dataSnapshot ->
            val messagesMap = dataSnapshot.value as? HashMap<String, Any>
            messagesMap?.let { map ->
                val newMessageList: MutableList<ChatMessage> = mutableListOf()
                map.forEach { (_, value) ->
                    val message = value as HashMap<String, Any>
                    val messageText = message["message"] as String
                    val sender = message["sender"] as String
                    val dateData = message["date"] as HashMap<String, Any>
                    val dateInMillis = dateData["time"] as Long
                    val chatMessage = ChatMessage(messageText, sender, Date(dateInMillis))
                    newMessageList.add(chatMessage)
                }
                val sortedMessages = newMessageList.sortedBy { it.date }
                Chats.addAll(sortedMessages)
                notifyDataSetChanged()
            }
        }
    }

}