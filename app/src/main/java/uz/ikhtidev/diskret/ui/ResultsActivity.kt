package uz.ikhtidev.diskret.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.ikhtidev.diskret.R
import uz.ikhtidev.diskret.adapters.ResultAdapter
import uz.ikhtidev.diskret.database.ThemeDatabase
import uz.ikhtidev.diskret.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityResultsBinding

    private val themeDatabase: ThemeDatabase by lazy {
        ThemeDatabase.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val themes = themeDatabase.themeDao().getResultThemes()
        val adapter = ResultAdapter(themes)
        binding.apply {
            btnBack.setOnClickListener {
                finish()
            }
            rvResults.adapter = adapter
            imageTest.setAnimation(R.raw.test)
            "${themes.size}/22".also { tvAnsweredTests.text = it }
            progressBar.progress = themes.size*100/22
        }

    }
}