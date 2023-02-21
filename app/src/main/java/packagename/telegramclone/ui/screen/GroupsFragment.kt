package packagename.telegramclone.ui.screen

import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import packagename.telegramclone.R
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.FragmentGroupsBinding
import packagename.telegramclone.presentation.GroupsViewModel
import packagename.telegramclone.ui.adapters.GroupsAdapter
import packagename.telegramclone.ui.dialogs.AddUserDialog
import java.util.*

class GroupsFragment: Fragment(R.layout.fragment_groups) {

    private lateinit var binding: FragmentGroupsBinding
    private val adapter = GroupsAdapter()
    private lateinit var viewModel: GroupsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GroupsViewModel::class.java)

        initObservers()

        binding.apply {
            recyclerView.adapter = adapter

            recyclerView.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )

            lifecycleScope.launchWhenResumed {
                viewModel.getUsers()
            }

            adapter.setOnItemClickListener {    id ->
                val bundle = Bundle()
                bundle.putString("id", id)
                findNavController().navigate(R.id.action_groupsScreen_to_chatFragment, bundle)
            }

            fab.setOnClickListener {

                val dialog = AddUserDialog()
                dialog.show(requireActivity().supportFragmentManager, dialog.tag)
            }
        }
    }

    private fun initObservers() {
        viewModel.activeUsersFlow.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)
    }
}