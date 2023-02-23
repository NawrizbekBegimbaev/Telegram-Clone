package packagename.telegramclone.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import packagename.telegramclone.data.User

class GroupsViewModel(application: Application): AndroidViewModel(application) {

    private val fb: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val realTimeFb = FirebaseDatabase.getInstance()

    val activeUsersFlow = MutableSharedFlow<List<User>>()
    val messageFlow = MutableSharedFlow<String>()
    val erroFlow = MutableSharedFlow<Throwable>()
    val getDocumentIdFlow = MutableSharedFlow<String>()

    suspend fun getUsers() {
        try {
            fb.collection("chaty").get().addOnSuccessListener {
                val list = mutableListOf<User>()
                viewModelScope.launch {
                    it.forEach {
                        val user = User(it.id, it.data["name"].toString())
                        list.add(user)
                    }
                    activeUsersFlow.emit(list)
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    messageFlow.emit(it.message ?: "")
                }
            }
        } catch (e: Exception) {
            viewModelScope.launch {
                erroFlow.emit(e)
            }
        }
    }

    fun addUser(user: User) {
        val map = mapOf(
            "id" to user.id,
            "name" to user.name
        )

        fb.collection("chaty").add(
            map
        ).addOnSuccessListener {
            viewModelScope.launch {

                getDocumentIdFlow.emit(it.id)
                realTimeFb.getReference("chats").child(it.id)
                    .addValueEventListener(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {

                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
            }
             Log.e("TTTT", it.id)
            }
        }
    }
