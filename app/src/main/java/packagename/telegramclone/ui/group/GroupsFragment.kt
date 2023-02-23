package packagename.telegramclone.ui.group

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import packagename.telegramclone.R
import packagename.telegramclone.data.LocalStorage
import packagename.telegramclone.databinding.FragmentGroupsBinding
import packagename.telegramclone.presentation.GroupsViewModel
import packagename.telegramclone.ui.adapters.GroupsAdapter
import packagename.telegramclone.ui.dialogs.AddUserDialog
import java.util.*

class GroupsFragment: Fragment(R.layout.fragment_groups) {

    private lateinit var binding: FragmentGroupsBinding
    private val adapter = GroupsAdapter()
    private lateinit var viewModel: GroupsViewModel
    private lateinit var docId: String
    private val navArgs: GroupsFragmentArgs by navArgs()

    val sharedPreferences = LocalStorage()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGroupsBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GroupsViewModel::class.java)

        initObservers()

        val userName = navArgs.username
        sharedPreferences.username = userName

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

            adapter.setOnItemClickListener {    id, name ->
                val bundle = Bundle()
                bundle.putString("id", id)
                bundle.putString("name", name)
                bundle.putString("username", userName)
                findNavController().navigate(R.id.action_groupsFragment_to_chatFragment, bundle)
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

        viewModel.getDocumentIdFlow.onEach {
            docId = it
            Log.w("TTTT", docId)
        }.launchIn(lifecycleScope)
    }
}