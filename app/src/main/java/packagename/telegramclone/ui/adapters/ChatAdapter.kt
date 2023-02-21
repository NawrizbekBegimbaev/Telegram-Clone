package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.ItemChatBinding

class ChatAdapter : ListAdapter<String, ChatAdapter.ChatViewHolder>(myDiffCallBack) {

    inner class ChatViewHolder(private val binding: ItemChatBinding) : ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(adapterPosition)

            binding.message.text = d
        }
    }

    private object myDiffCallBack: DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
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