package packagename.telegramclone.ui.group

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import packagename.telegramclone.R
import packagename.telegramclone.data.local.LocalStorage
import packagename.telegramclone.databinding.FragmentGroupsBinding
import packagename.telegramclone.presentation.groups.GroupsViewModel
import packagename.telegramclone.ui.adapters.GroupsAdapter
import ru.ldralighieri.corbind.view.clicks

class GroupsFragment: Fragment(R.layout.fragment_groups) {

    private lateinit var binding: FragmentGroupsBinding
    private var _adapter: GroupsAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel by viewModel<GroupsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)


        initData()
        initObservers()
        initListeners()


    }

    private fun initListeners() {
        val drawer = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        adapter.setOnItemClickListener { id, name ->
            findNavController().navigate(
                GroupsFragmentDirections.actionGroupsFragmentToChatFragment(id, name)
            )
        }

        binding.fab.clicks().debounce(200).onEach {
            findNavController().navigate(
                GroupsFragmentDirections.actionGroupsScreenToAddGroupFragment()
            )
        }.launchIn(lifecycleScope)

        binding.menu.clicks().debounce(200).onEach {
            drawer.open()
        }.launchIn(lifecycleScope)
    }

    private fun initObservers() {
        viewModel.getGroupChatsFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }

    private fun initData() {
        _adapter = GroupsAdapter()
        binding.recyclerView.adapter = adapter

        lifecycleScope.launchWhenResumed {
            viewModel.getGroupsChats()
        }
    }
}
