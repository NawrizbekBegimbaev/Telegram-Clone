package packagename.telegramclone.data

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import packagename.telegramclone.BuildConfig

class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }


}