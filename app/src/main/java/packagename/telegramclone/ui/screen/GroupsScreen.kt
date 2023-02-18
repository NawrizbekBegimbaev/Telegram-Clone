package packagename.telegramclone.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import packagename.telegramclone.R
import packagename.telegramclone.databinding.ItemGroupsBinding
import packagename.telegramclone.databinding.ScreenGroupsBinding
import packagename.telegramclone.ui.adapters.ItemGroupsAdapter

class GroupsScreen: Fragment(R.layout.screen_groups) {

    private lateinit var binding: ScreenGroupsBinding
    private val adapter = ItemGroupsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ScreenGroupsBinding.bind(view)

        binding.apply {
            recyclerView.adapter = adapter
        }
    }
}