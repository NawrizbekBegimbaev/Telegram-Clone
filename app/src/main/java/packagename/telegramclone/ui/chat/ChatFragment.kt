package packagename.telegramclone.ui.chat

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import packagename.telegramclone.R
import packagename.telegramclone.data.MessageData
import packagename.telegramclone.databinding.FragmentChatBinding
import packagename.telegramclone.presentation.chats.ChatsViewModel
import packagename.telegramclone.presentation.groups.GroupsViewModel
import packagename.telegramclone.ui.adapters.ChatAdapter
import ru.ldralighieri.corbind.view.clicks
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ChatFragment : Fragment(R.layout.fragment_chat) {

    private val binding by viewBinding(FragmentChatBinding::bind)
    private val args: ChatFragmentArgs by navArgs()
    private var _adapter: ChatAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel by viewModel<ChatsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        initData()
        initObservers()
        initListeners()


    }

    private fun initListeners() {
        binding.btnBack.clicks().debounce(200).onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)

        binding.etMessage.addTextChangedListener {
            if (it.toString().isNotEmpty()) {
                binding.icAttach.visibility = View.GONE
                binding.icVideo.visibility = View.GONE
                binding.icSend.visibility = View.VISIBLE
            } else {
                binding.icAttach.visibility = View.VISIBLE
                binding.icVideo.visibility = View.VISIBLE
                binding.icSend.visibility = View.GONE
            }
        }

        binding.icSend.clicks().debounce(200).onEach {
            val message = binding.etMessage.text.toString()
            if (it.toString().isNotEmpty()) {
                viewModel.sendMessage(message, args.idUser)
                binding.etMessage.setText("")
            } else {
                binding.icAttach.visibility = View.VISIBLE
                binding.icVideo.visibility = View.VISIBLE
                binding.icSend.visibility = View.GONE
            }
        }.launchIn(lifecycleScope)


    }

    private fun initObservers() {
        FirebaseDatabase.getInstance().getReference("chats").child(args.idUser)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val list = mutableListOf<MessageData>()
                        snapshot.children.forEach {
                            val item = it.value as HashMap<*, *>
                            list.add(
                                MessageData(
                                    item["message"].toString(),
                                    item["user"].toString(),
                                    item["time"].toString()
                                )
                            )
                        }
                        adapter.submitList(list)
                        if (list.isNotEmpty()) {
                            binding.recyclerViewChat.smoothScrollToPosition(list.lastIndex)
                            val audio =
                                MediaPlayer.create(requireContext(), R.raw.new_message_sound)
                            audio.start()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun initData() {
        binding.tvGroupName.text = args.name
        _adapter = ChatAdapter()
        binding.recyclerViewChat.adapter = adapter

    }


}