package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.MessageData
import packagename.telegramclone.data.local.LocalStorage
import packagename.telegramclone.databinding.ItemChatAnotherBinding
import packagename.telegramclone.databinding.ItemChatMeBinding

class ChatAdapter : ListAdapter<MessageData, RecyclerView.ViewHolder>(myDiffCallBack) {


    companion object {
        const val ME = 0
        const val ANOTHER = 1
    }
    inner class ChatAnotherViewHolder(private val binding: ItemChatAnotherBinding) : ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(absoluteAdapterPosition)

            binding.apply {
                tvMessage.text = d.message
                tvTime.text = d.time
                tvUsername.text = d.from
            }
        }
    }

    inner class ChatMeViewHolder(private val binding: ItemChatMeBinding) : ViewHolder(binding.root) {
        fun bind() {
            val d = getItem(absoluteAdapterPosition)

            binding.apply {
                tvTime.text = d.time
                tvMessage.text = d.message
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

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).from) {
            LocalStorage().username -> ME
            else -> ANOTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ME -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_chat_me, parent, false
                )
                val binding = ItemChatMeBinding.bind(v)
                ChatMeViewHolder(binding)
            }
            else -> {
                val v = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_chat_another, parent, false
                )
                val binding = ItemChatAnotherBinding.bind(v)
                ChatAnotherViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItem(position).from) {
            LocalStorage().username -> {
                (holder as ChatMeViewHolder).bind()
            }
            else -> {
                (holder as ChatAnotherViewHolder).bind()
            }
        }
    }
}