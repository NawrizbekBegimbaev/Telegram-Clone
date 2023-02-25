package packagename.telegramclone.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import packagename.telegramclone.R
import packagename.telegramclone.data.GroupData
import packagename.telegramclone.databinding.ItemGroupsBinding

class GroupsAdapter: ListAdapter<GroupData, GroupsAdapter.ItemGroupsViewHolder>(diffUtilCallBack) {
    
    inner class ItemGroupsViewHolder(private val binding: ItemGroupsBinding): ViewHolder(binding.root) {

        fun bind() {
            val d = getItem(adapterPosition)
            binding.apply {
                tvName.text = d.name

                binding.root.setOnClickListener {
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

    private object diffUtilCallBack: DiffUtil.ItemCallback<GroupData>() {
        override fun areItemsTheSame(oldItem: GroupData, newItem: GroupData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GroupData, newItem: GroupData): Boolean {
            return oldItem == newItem
        }
    }

    private var onItemClick: (id: String, name: String) -> Unit = {_, _ ->}
    fun setOnItemClickListener(onItemClick: (id: String, name: String) -> Unit) {
         this.onItemClick = onItemClick
    }
}

