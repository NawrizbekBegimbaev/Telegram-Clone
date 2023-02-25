package packagename.telegramclone.presentation.groups

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import packagename.telegramclone.data.GroupData
import packagename.telegramclone.data.ResultData
import packagename.telegramclone.domain.MainRepository

class GroupsViewModel(application: Application) : AndroidViewModel(application) {

    val getGroupChatsFlow = MutableSharedFlow<List<GroupData>>()
    val messageFlow = MutableSharedFlow<String>()
    val erroFlow = MutableSharedFlow<Throwable>()
    val repo = MainRepository(FirebaseFirestore.getInstance(), FirebaseDatabase.getInstance())

    suspend fun getGroupsChats() {
        repo.getGroupChatsFlow().onEach {
            when (it) {
                is ResultData.Success -> {
                    getGroupChatsFlow.emit(it.data)
                }
                is ResultData.Message -> {
                    messageFlow.emit(it.message)
                }
                is ResultData.Error -> {
                    erroFlow.emit(it.error)
                }
            }
        }.launchIn(viewModelScope)
    }

}
