package packagename.telegramclone

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import packagename.telegramclone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        navController = navHostFragment.navController

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_new_group -> {
                }
                R.id.nav_contacts -> {
                }
                R.id.nav_calls -> {
                }
                R.id.nav_people_nearby -> {
                }
                R.id.nav_saved_message -> {
                }
                R.id.nav_settings -> {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                    navController.navigate(
                        R.id.action_groupsScreen_to_editFragment
                    )
                }
            }
            true
        }


        val shared = getSharedPreferences("ChatAppSharedPref", Context.MODE_PRIVATE)

        val name = shared.getString("name", "asdasd")
        shared.edit().putString("name","Rasul0702").apply()




    }
}