package uz.ikhtidev.diskret.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.ikhtidev.diskret.adapters.ThemeAdapter
import uz.ikhtidev.diskret.database.ThemeDatabase
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val themeDatabase: ThemeDatabase by lazy {
        ThemeDatabase.getInstance(this)
    }
    private lateinit var themes: ArrayList<Theme>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        themes = ArrayList(themeDatabase.themeDao().getAllThemes())
        val themeAdapter = ThemeAdapter(themes)
        binding.rvTheme.adapter = themeAdapter
    }

}
