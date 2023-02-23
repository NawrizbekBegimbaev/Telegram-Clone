package packagename.telegramclone.ui.chat

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param
import com.google.firebase.database.ServerValue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import packagename.telegramclone.R
import packagename.telegramclone.databinding.FragmentChatBinding
import packagename.telegramclone.presentation.ChatsViewModel
import packagename.telegramclone.presentation.GroupsViewModel
import packagename.telegramclone.ui.adapters.ChatAdapter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


@Suppress("UNREACHABLE_CODE")
class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding

    private lateinit var viewModel: ChatsViewModel
    private lateinit var groupsViewModel: GroupsViewModel

    private val adapter = ChatAdapter()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ChatsViewModel::class.java)

        groupsViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GroupsViewModel::class.java)

        val docId = arguments?.getString("id") ?: ""
        val name = arguments?.getString("name") ?: ""
        val userNameInput = arguments?.getString("username") ?: ""
        Log.d("TTTT", docId)

        initObservers()

        binding.apply {

            recyclerViewChat.adapter = adapter

            userName.text = name

            lifecycleScope.launchWhenResumed {
                viewModel.getMessage(docId)
                Log.d("TTTT", "$docId -> ")
                recyclerViewChat.smoothScrollToPosition(adapter.itemCount)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack(R.id.groupsScreen, false)
            }

            tilName.setEndIconOnClickListener {
                lifecycleScope.launchWhenResumed {
                    val currentDate = date()
                    if (binding.etName.text?.isNotEmpty() == true) {
                        viewModel.sendMessage(etName.text.toString(), currentDate, docId, userNameInput)
                        etName.text?.clear()
                    }
                }
            }
        }
    }
    private fun initObservers() {
        viewModel.getMessageFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

        /*groupsViewModel.getDocumentIdFlow.onEach {
            lifecycleScope.launchWhenResumed {
                documentId = it
                Log.d("TTTT", documentId)
            }
        }.launchIn(lifecycleScope)*/
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun date(): String {
        val currentDate = System.currentTimeMillis()
        val dateFormat: DateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
        val dateText: String = dateFormat.format(currentDate)
        val timeFormat: DateFormat = SimpleDateFormat("hhmmss", Locale.getDefault())
        val timeText: String = timeFormat.format(currentDate)


        return "$dateText$timeText"

        //return ServerValue.TIMESTAMP.toString()
        //println(timestamp) // 01.01.2017 13.59.13
        /*return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo"))
            .format(DateTimeFormatter.ofPattern("MMddyyyhhmmss"))*/
/*
        val timeStampNow:Map<String, String> = ServerValue.TIMESTAMP
        Log.e("timeNokis", timeStampNow["timestamp"]?.toLong().toString())
        return timeStampNow["timestamp"]?.toLong().toString()*/

//        val timestamp = dataSnapshot.child("timestamp").getValue() as Long

    }

}