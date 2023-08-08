package uz.ikhtidev.diskret.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.ikhtidev.diskret.R
import uz.ikhtidev.diskret.database.ThemeDatabase
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.databinding.ActivityThemeDetailBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_NUMBER
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_TYPE
import uz.ikhtidev.diskret.utils.TYPE

class ThemeDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeDetailBinding
    private val themeDatabase: ThemeDatabase by lazy {
        ThemeDatabase.getInstance(this)
    }
    private lateinit var themes: ArrayList<Theme>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        themes = ArrayList(themeDatabase.themeDao().getAllThemes())
        binding.apply {
            toolbarName.text = themes[FILE_NUMBER].name
            imageMaruza.setAnimation(R.raw.maruza)
            imageMuammoli.setAnimation(R.raw.muammoli)
            imageMustaqil.setAnimation(R.raw.mustaqil_ish)
            imagePresentation.setAnimation(R.raw.presentation)
            imageTest.setAnimation(R.raw.test)
            btnBack.setOnClickListener {
                finish()
            }
            btnMaruza.setOnClickListener {
                openPdfActivity(TYPE.M.fileType)
            }
            btnMuammoli.setOnClickListener {
                openPdfActivity(TYPE.V.fileType)
            }
            btnMustaqil.setOnClickListener {
                openPdfActivity(TYPE.I.fileType)
            }
            btnTaqdimot.setOnClickListener {
                openPdfActivity(TYPE.P.fileType)
            }
            btnTest.setOnClickListener {
                openPdfActivity(TYPE.T.fileType)
            }
        }
    }

    private fun openPdfActivity(fileType: String) {
        FILE_TYPE = fileType

        when(fileType){
            TYPE.M.fileType->{
                val theme = themes[FILE_NUMBER]
                theme.isRead=1
                themeDatabase.themeDao().updateTheme(theme)
                startActivity(Intent(this, PdfViewerActivity::class.java))
            }
            TYPE.V.fileType,TYPE.I.fileType,TYPE.P.fileType,TYPE.T.fileType->{
                val isRead:Int = ArrayList(themeDatabase.themeDao().getAllThemes())[FILE_NUMBER].isRead
                if (isRead == 0){
                    Toast.makeText(this, getString(R.string.required_read_lecture), Toast.LENGTH_SHORT).show()
                }else{
                    if (fileType == TYPE.T.fileType){
                        if (themeDatabase.questionDao().getAllQuestionsByThemeId((FILE_NUMBER).toLong()).isNotEmpty())
                        startActivity(Intent(this, QuizActivity::class.java))
                        else Toast.makeText(
                            this,
                            getString(R.string.test_not_yet),
                            Toast.LENGTH_SHORT
                        ).show()
                    }else {
                        startActivity(Intent(this, PdfViewerActivity::class.java))
                    }
                }
            }
        }
    }
}