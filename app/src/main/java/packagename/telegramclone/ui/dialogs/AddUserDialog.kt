package packagename.telegramclone.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import packagename.telegramclone.R
import packagename.telegramclone.data.User
import packagename.telegramclone.databinding.DialogAddUserBinding
import packagename.telegramclone.presentation.GroupsViewModel
import java.util.*

class AddUserDialog : DialogFragment(R.layout.dialog_add_user) {

    private lateinit var binding: DialogAddUserBinding
    private lateinit var viewModel: GroupsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogAddUserBinding.bind(view)

        viewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
        ).get(GroupsViewModel::class.java)

        binding.apply {
            btnAdd.setOnClickListener {
                val name = etName.text.toString()
                val uid = uniqueId()

                if (name.isNotEmpty()) {
                    lifecycleScope.launchWhenResumed {
                        viewModel.addUser(
                            User(uid, name)
                        )
                        onAddSuccess.invoke(uid)
                        viewModel.getUsers()
                        dismiss()
                    }
                } else {
                    Toast.makeText(requireContext(), "Toltir", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    private fun uniqueId():String = UUID.randomUUID().toString()

    private var onAddSuccess: (id: String) -> Unit = {}
    fun setOnAddSuccessListener(onAddSuccess: (id: String) -> Unit) {
        this.onAddSuccess = onAddSuccess
    }
}