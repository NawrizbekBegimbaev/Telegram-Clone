package packagename.telegramclone.ui.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import packagename.telegramclone.R
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.FragmentChatBinding
import packagename.telegramclone.presentation.ChatsViewModel
import packagename.telegramclone.presentation.GroupsViewModel
import packagename.telegramclone.ui.adapters.ChatAdapter

class ChatFragment : Fragment(R.layout.fragment_chat) {

    private lateinit var binding: FragmentChatBinding

    private lateinit var viewModel: ChatsViewModel

    private val adapter = ChatAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentChatBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(ChatsViewModel::class.java)

        val uid = arguments?.getString("id") ?: ""

        initObservers()

        binding.apply {

            recyclerViewChat.adapter = adapter

            lifecycleScope.launchWhenResumed {
                viewModel.getMessage(uid)
                recyclerViewChat.smoothScrollToPosition(adapter.itemCount)
            }

            btnBack.setOnClickListener {
                findNavController().popBackStack(R.id.groupsScreen, false)
            }

            tilName.setEndIconOnClickListener {
                lifecycleScope.launchWhenResumed {

                    viewModel.sendMessage(etName.text.toString(), uid)
                }
            }
        }
    }
    private fun initObservers() {
        viewModel.getMessageFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}