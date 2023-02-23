package packagename.telegramclone.presentation

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import packagename.telegramclone.data.Chat
import java.util.*

class ChatsViewModel(application: Application) : AndroidViewModel(application) {

    private val realTimeDb: FirebaseDatabase = FirebaseDatabase.getInstance()

    val getMessageFlow = MutableSharedFlow<MutableList<Chat>>()

    fun sendMessage(message: String, currentDateId: String, groupId: String, from: String) {

        val time = System.currentTimeMillis().toString()

        val data = mapOf(
            "message" to message,
            "from" to from
        )

        /*realTimeDb.getReference("chats").child(documentId).child(time).child("message").setValue(
            message
        )

        realTimeDb.getReference("chats").child(documentId).child(time).child("from").setValue(
            from
        )*/

        realTimeDb.getReference("chats").child(groupId).child(time).setValue(data)

    }

    suspend fun getMessage(docId: String) {
        realTimeDb.getReference("chats").child(docId)
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("SuspiciousIndentation")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val tempList = mutableListOf<Chat>()
                    viewModelScope.launch {
                        snapshot.children.forEach { data ->

                            val d = data.value as HashMap<*, *>

                            val chat = Chat(d["message"].toString(), d["from"].toString())
                            //val user = User("kdsjfsjf", data.value.toString())
                            tempList.add(chat)

                        }
                        Log.d("TTTT", "$tempList")
                        getMessageFlow.emit(tempList)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun uniqueId(): String = UUID.randomUUID().toString()

    fun date() {
    }
}