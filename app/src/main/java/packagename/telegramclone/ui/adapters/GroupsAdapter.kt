package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.ItemGroupsBinding

class GroupsAdapter: ListAdapter<User, GroupsAdapter.ItemGroupsViewHolder>(diffUtilCallBack) {
    
    inner class ItemGroupsViewHolder(private val binding: ItemGroupsBinding): ViewHolder(binding.root) {

        fun bind() {
            val d = getItem(adapterPosition)
            binding.apply {
                tvName.text = d.name

                tvName.setOnClickListener {
                    onItemClick.invoke(d.id, d.name)
                }
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

    private object diffUtilCallBack: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClick: (id: String, name: String) -> Unit = {_, _ ->}
    fun setOnItemClickListener(onItemClick: (id: String, name: String) -> Unit) {
         this.onItemClick = onItemClick
    }
}

