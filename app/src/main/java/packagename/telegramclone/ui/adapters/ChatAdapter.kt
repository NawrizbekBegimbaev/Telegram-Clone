package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.MessageData
import packagename.telegramclone.data.local.LocalStorage
import packagename.telegramclone.databinding.ItemChatBinding

class ChatAdapter : ListAdapter<MessageData, ChatAdapter.ChatViewHolder>(myDiffCallBack) {

    val sharedPreferences = LocalStorage()

    inner class ChatViewHolder(private val binding: ItemChatBinding) : ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(adapterPosition)

            binding.menden.isVisible = false
            binding.userden.isVisible = false

            if (d.from == sharedPreferences.username) {
                binding.message.text = d.message
                binding.timeMenden.text = d.time
                binding.menden.isVisible = true
            } else {
                binding.userden.isVisible = true
                binding.messageUserden.text = d.message
                binding.userNameUserden.text = d.from
                binding.timeUser.text = d.time
            }

        }
    }

    private object myDiffCallBack: DiffUtil.ItemCallback<MessageData>() {
        override fun areItemsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MessageData, newItem: MessageData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        val binding = ItemChatBinding.bind(v)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind()
    }
}