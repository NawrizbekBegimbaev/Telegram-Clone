package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.Chat
import packagename.telegramclone.data.LocalStorage
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.ItemChatBinding

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ChatViewHolder>(myDiffCallBack) {

    val sharedPreferences = LocalStorage()

    inner class ChatViewHolder(private val binding: ItemChatBinding) : ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(adapterPosition)

            binding.menden.isVisible = false
            binding.userden.isVisible = false

            if (d.from == sharedPreferences.username) {
                binding.message.text = d.message
                binding.menden.isVisible = true
            } else {
                binding.userden.isVisible = true
                binding.messageUserden.text = d.message
            }

        }
    }

    private object myDiffCallBack: DiffUtil.ItemCallback<Chat>() {
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
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