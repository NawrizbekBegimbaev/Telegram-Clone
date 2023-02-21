package packagename.telegramclone.presentation

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*

class ChatsViewModel(application: Application) : AndroidViewModel(application) {

    var id = 0

    private val realTimeDb: FirebaseDatabase = FirebaseDatabase.getInstance()

    val getMessageFlow = MutableSharedFlow<MutableList<String>>()

    fun sendMessage(message: String, uid: String) {
        realTimeDb.getReference("chats").child(uid).child("${id++}").setValue(
            message
        )

    }

    suspend fun getMessage(uidUser: String) {
        realTimeDb.getReference("chats").child(uidUser).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = mutableListOf<String>()
                viewModelScope.launch {
                    snapshot.children.forEach { data ->

                        //val d = data.value as HashMap<*, *>

                        val message = data.value.toString()
                            //val user = User("kdsjfsjf", data.value.toString())
                            tempList.add(message)

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

    private fun uniqueId():String = UUID.randomUUID().toString()
}