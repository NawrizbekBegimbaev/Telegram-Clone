package packagename.telegramclone.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import packagename.telegramclone.R
import packagename.telegramclone.data.local.LocalStorage
import packagename.telegramclone.databinding.FragmentEditBinding
import ru.ldralighieri.corbind.view.clicks

class EditFragment : Fragment(R.layout.fragment_edit) {

    private val binding by viewBinding(FragmentEditBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etName.setText(LocalStorage().username)

        binding.icDone.clicks().debounce(200).onEach {
            val name = binding.etName.text.toString()
            if (name.isNotEmpty()) {
                LocalStorage().username = name
                Snackbar.make(
                    requireView(), "User name has been successfuly updated.", Snackbar.LENGTH_SHORT
                ).show()
                findNavController().popBackStack()
            }
        }.launchIn(lifecycleScope)

        binding.icBack.clicks().debounce(200).onEach {
            findNavController().popBackStack()
        }.launchIn(lifecycleScope)
    }
}