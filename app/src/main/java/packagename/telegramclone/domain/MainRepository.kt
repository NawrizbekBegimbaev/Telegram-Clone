package packagename.telegramclone.domain

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import packagename.telegramclone.data.GroupData
import packagename.telegramclone.data.ResultData
import packagename.telegramclone.data.local.LocalStorage
import packagename.telegramclone.utils.toTime


class MainRepository(private val fb: FirebaseFirestore, private val rd: FirebaseDatabase) {

    suspend fun getGroupChatsFlow() = flow<ResultData<List<GroupData>>> {
        emit(
            ResultData.Success(fb.collection("chaty").get().await().documents.mapNotNull {
                GroupData(it.id, it.data!!["name"].toString()
                )
            })
        )
    }.catch {
        emit(ResultData.Error(it))
    }

    suspend fun addGroup(name: String) = flow {
        val data = mapOf(
            "name" to name
        )

        fb.collection("chaty").document().set(data)
        emit(fb.collection("chaty").whereEqualTo("name", name).get().await().documents.first().id)
    }.catch {
        it.printStackTrace()
    }

    suspend fun sendMessage(message: String, groupId: String) {
        val time = System.currentTimeMillis()
        val data = mapOf(
            "message" to message,
            "user" to LocalStorage().username,
            "time" to time.toString().toTime()
        )
        rd.getReference("chats").child(groupId).child(time.toString()).setValue(data)
    }

    suspend fun addGroupToRealtimeDatabase(id: String) {
        rd.getReference("chats").child(id).setValue(null)
    }

}

