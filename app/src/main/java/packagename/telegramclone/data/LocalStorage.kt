package packagename.telegramclone.data

import android.content.Context
import android.content.SharedPreferences
import com.example.chatappwithfirebase.utils.StringPreference

class LocalStorage() {

    companion object {
        val prefs: SharedPreferences =
            App.instance.getSharedPreferences("ChatAppSharePrefs", Context.MODE_PRIVATE)
    }

    var username by StringPreference(prefs, "temp0934")
}