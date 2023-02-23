package packagename.telegramclone.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import packagename.telegramclone.R
import packagename.telegramclone.databinding.FragmentInputUsernameBinding

class UserNameInputFragment : Fragment(R.layout.fragment_input_username) {

    private lateinit var binding: FragmentInputUsernameBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInputUsernameBinding.bind(view)
        binding.fabNext.setOnClickListener {
            if (binding.etUsername.text.isNotEmpty()) {
                findNavController().navigate(
                    UserNameInputFragmentDirections.actionUserNameInputFragmentToGroupsFragment(
                        binding.etUsername.text.toString()
                    )
                )
            } else {
                Toast.makeText(requireContext(), "Введите свое имя", Toast.LENGTH_SHORT).show()
            }
        }
    }
}