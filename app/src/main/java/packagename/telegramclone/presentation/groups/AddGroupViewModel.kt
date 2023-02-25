package packagename.telegramclone.presentation.groups

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import packagename.telegramclone.domain.MainRepository

class AddGroupViewModel(application: Application) : AndroidViewModel(application) {

    val repo = MainRepository(FirebaseFirestore.getInstance(), FirebaseDatabase.getInstance())

    val addGroupSuccessFlow = MutableSharedFlow<String>()
    suspend fun addGroup(name: String) {
        repo.addGroup(name).onEach {
            repo.addGroupToRealtimeDatabase(it)
            addGroupSuccessFlow.emit(it)
        }.launchIn(viewModelScope)
    }
}