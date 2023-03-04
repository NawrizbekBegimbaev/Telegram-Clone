package packagename.telegramclone.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import packagename.telegramclone.presentation.chats.ChatsViewModel
import packagename.telegramclone.presentation.groups.AddGroupViewModel
import packagename.telegramclone.presentation.groups.GroupsViewModel
import packagename.telegramclone.ui.adapters.ChatAdapter
import packagename.telegramclone.ui.adapters.GroupsAdapter

val appModule = module {


    viewModel<GroupsViewModel>() {
        GroupsViewModel()
    }

    viewModel {
        ChatsViewModel()
    }

    viewModel {
        AddGroupViewModel()
    }

}