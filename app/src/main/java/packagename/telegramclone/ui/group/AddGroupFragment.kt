package packagename.telegramclone.ui.group

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import packagename.telegramclone.R
import packagename.telegramclone.databinding.FragmentAddGroupBinding
import packagename.telegramclone.presentation.groups.AddGroupViewModel
import ru.ldralighieri.corbind.view.clicks

class AddGroupFragment : Fragment(R.layout.fragment_add_group) {

    private lateinit var viewModel: AddGroupViewModel
    private val binding by viewBinding(FragmentAddGroupBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        )[AddGroupViewModel::class.java]

        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel.addGroupSuccessFlow.onEach {
            Snackbar.make(
                requireView(), "Group was successfuly created", Snackbar.LENGTH_SHORT
            ).show()
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }

    private fun initListeners() {
        binding.icDone.clicks().debounce(200).onEach {
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                viewModel.addGroup(name)
            } else {
                Snackbar.make(
                    requireView(), "Please Enter name of your group!", Snackbar.LENGTH_SHORT
                ).show()
            }
        }.launchIn(lifecycleScope)

        binding.icBack.clicks().debounce(200).onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }


}

