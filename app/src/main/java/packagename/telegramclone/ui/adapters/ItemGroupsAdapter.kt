package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.ItemGroupsBinding

class ItemGroupsAdapter: ListAdapter<User, ItemGroupsAdapter.ItemGroupsViewHolder>(DiffUtilCallBack()) {
    
    inner class ItemGroupsViewHolder(private val binding: ItemGroupsBinding): ViewHolder(binding.root) {

        fun bind() {
            val d = getItem(adapterPosition)
            binding.apply {
                name.text = d.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemGroupsViewHolder {
        return ItemGroupsViewHolder(
            ItemGroupsBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_groups, parent, false
                )
            )
        )

    }

    override fun onBindViewHolder(holder: ItemGroupsViewHolder, position: Int) {
        holder.bind()
    }
}

private class DiffUtilCallBack: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}