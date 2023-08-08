package uz.ikhtidev.diskret.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import uz.ikhtidev.diskret.R
import uz.ikhtidev.diskret.database.ThemeDatabase
import uz.ikhtidev.diskret.database.entity.Question
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.database.entity.Variant
import uz.ikhtidev.diskret.databinding.ActivitySplashBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.THEME_LIST
import java.io.IOException

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val themeDatabase: ThemeDatabase by lazy {
        ThemeDatabase.getInstance(this)
    }
    private lateinit var themes: ArrayList<Theme>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.hide()

        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Default) {
                createThemeList()
            }

            withContext(Dispatchers.Default) {
                addThemesToDb()
            }

            withContext(Dispatchers.IO) {
                addQuestionsToDb()
            }

            withContext(Dispatchers.Default) {
                startMainActivity()
            }

        }

        binding.imageLoading.setAnimation(R.raw.loading)
    }

    private fun writeOneQuestionToDb(filePath: Int) {

        try {
            val input = assets.open("$filePath/$filePath.html")
            val document: Document = Jsoup.parse(input, "windows-1252", "http://ikhtidev.uz/")

            // Change the value of the src attribute of the img tag
            val imgElements = document.select("img")
            for (img in imgElements) {
                var imgPath = img.attr("src")
                imgPath = "file:///android_asset/$filePath/$imgPath"
                img.attr("src", imgPath)
            }

            // All tables
            val tables = document.select("table")

            // Each table
            for (table in tables) {
                val rows: Elements = table.select("tr")

                val questionAndVariants: Elements = rows.select("td")
                var questionId: Long = -1
                for (i in 0 until questionAndVariants.size) {

                    if (i == 0) {
                        themeDatabase.questionDao().addQuestion(
                            Question(
                                title = "<b>${questionAndVariants[i]}</b",
                                themeId = (filePath - 1)
                            )
                        ).also { questionId = it }
                    } else {
                        if (i == 1) {
                            themeDatabase.variantDao().addVariant(
                                Variant(
                                    title = questionAndVariants[i].toString(),
                                    isTrue = true,
                                    questionOwnerId = questionId
                                )
                            )
                        } else {
                            themeDatabase.variantDao().addVariant(
                                Variant(
                                    title = questionAndVariants[i].toString(),
                                    isTrue = false,
                                    questionOwnerId = questionId
                                )
                            )
                        }
                    }
                }

            }

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun startMainActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    private suspend fun addQuestionsToDb() {
        val questions = ArrayList(themeDatabase.questionDao().getAllQuestions())
        if (questions.isEmpty()) {
            for (i in 1..22) {
                writeOneQuestionToDb(i)
            }
        } else {
            delay(2000)
        }
    }

    private fun addThemesToDb() {
        themes = ArrayList(themeDatabase.themeDao().getAllThemes())
        if (themes.isEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                THEME_LIST.forEach { theme ->
                    themeDatabase.themeDao().addTheme(theme)
                }
            }
        }
    }

    private fun createThemeList() {
        THEME_LIST.add(Theme(name = getString(R.string.theme_1)))  //1
        THEME_LIST.add(Theme(name = getString(R.string.theme_2)))  //2
        THEME_LIST.add(Theme(name = getString(R.string.theme_3)))  //3
        THEME_LIST.add(Theme(name = getString(R.string.theme_4)))  //4
        THEME_LIST.add(Theme(name = getString(R.string.theme_5)))  //5
        THEME_LIST.add(Theme(name = getString(R.string.theme_6)))  //6
        THEME_LIST.add(Theme(name = getString(R.string.theme_7)))  //7
        THEME_LIST.add(Theme(name = getString(R.string.theme_8)))  //8
        THEME_LIST.add(Theme(name = getString(R.string.theme_9)))  //9
        THEME_LIST.add(Theme(name = getString(R.string.theme_10))) //10
        THEME_LIST.add(Theme(name = getString(R.string.theme_11))) //11
        THEME_LIST.add(Theme(name = getString(R.string.theme_12))) //12
        THEME_LIST.add(Theme(name = getString(R.string.theme_13))) //13
        THEME_LIST.add(Theme(name = getString(R.string.theme_14))) //14
        THEME_LIST.add(Theme(name = getString(R.string.theme_15))) //15
        THEME_LIST.add(Theme(name = getString(R.string.theme_16))) //16
        THEME_LIST.add(Theme(name = getString(R.string.theme_17))) //17
        THEME_LIST.add(Theme(name = getString(R.string.theme_18))) //18
        THEME_LIST.add(Theme(name = getString(R.string.theme_19))) //19
        THEME_LIST.add(Theme(name = getString(R.string.theme_20))) //20
        THEME_LIST.add(Theme(name = getString(R.string.theme_21))) //21
        THEME_LIST.add(Theme(name = getString(R.string.theme_22))) //22
    }
}