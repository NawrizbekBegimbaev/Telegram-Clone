package packagename.telegramclone.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import packagename.telegramclone.data.User

class GroupsViewModel(application: Application): AndroidViewModel(application) {

    private var fb: FirebaseFirestore = FirebaseFirestore.getInstance()

    val activeUsersFlow = MutableSharedFlow<List<User>>()
    val messageFlow = MutableSharedFlow<String>()
    val erroFlow = MutableSharedFlow<Throwable>()
    val getIdFlow = MutableSharedFlow<String>()

    suspend fun getUsers() {
        try {
            fb.collection("chaty").get().addOnSuccessListener {
                val list = mutableListOf<User>()
                viewModelScope.launch {
                    it.forEach {
                        val user = User(it.data["id"].toString(), it.data["name"].toString())
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

    suspend fun addUser(user: User) {
        val map = mapOf(
            "id" to user.id,
            "name" to user.name
        )

        fb.collection("chaty").add(
            map
        ).addOnSuccessListener {

        }
    }
}