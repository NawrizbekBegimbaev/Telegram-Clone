package packagename.telegramclone.di

import org.koin.dsl.module
import packagename.telegramclone.domain.MainRepository


val domainModule = module {

    single {
        MainRepository(fb = get(), rd = get())
    }
}