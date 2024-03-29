package packagename.telegramclone.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import packagename.telegramclone.app.App
import java.text.SimpleDateFormat
import java.util.*

fun String.toTime(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.ROOT)
    simpleDateFormat.timeZone = TimeZone.getTimeZone("GMT+5")
    return simpleDateFormat.format(this.toLong())
}


fun toast(message: String) {
    Toast.makeText(App.instance, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.snackBar(message: String) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_SHORT).show()
}