package packagename.telegramclone.presentation.chats

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import packagename.telegramclone.data.MessageData
import packagename.telegramclone.domain.MainRepository
import java.util.*

class ChatsViewModel() : ViewModel() {

    val repo = MainRepository(FirebaseFirestore.getInstance(), FirebaseDatabase.getInstance())

    suspend fun sendMessage(message: String, groupId: String,) {

        repo.sendMessage(message, groupId)

    }
}