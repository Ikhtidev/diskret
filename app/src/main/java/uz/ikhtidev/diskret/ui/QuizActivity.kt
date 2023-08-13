package uz.ikhtidev.diskret.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.transition.TransitionManager
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.ikhtidev.diskret.R
import uz.ikhtidev.diskret.adapters.QuizAdapter
import uz.ikhtidev.diskret.database.ThemeDatabase
import uz.ikhtidev.diskret.database.entity.QuestionWithVariant
import uz.ikhtidev.diskret.databinding.ActivityQuizBinding
import uz.ikhtidev.diskret.utils.Constants.Companion.FILE_NUMBER

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val themeDatabase: ThemeDatabase by lazy {
        ThemeDatabase.getInstance(this)
    }
    private lateinit var themeQuestions: List<QuestionWithVariant>
    private lateinit var timer: CountDownTimer
    private var currentPage = 0
    private var testsCount = 0
    private var testResult = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        themeQuestions = themeDatabase.questionDao().getQuestionsWithVariants(FILE_NUMBER)

        testsCount = themeQuestions.size
        for (themeQuestion in themeQuestions) {
            val shuffledVariants = themeQuestion.variants.shuffled()
            themeQuestion.variants = shuffledVariants
        }

        val quizAdapter = QuizAdapter(this, themeQuestions)

         binding.apply {
             binding.viewPager.apply {
                 adapter = quizAdapter
                 isUserInputEnabled = false
             }
             tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                 override fun onTabSelected(tab: TabLayout.Tab?) {
                     currentPage = tab!!.position
                 }

                 override fun onTabUnselected(tab: TabLayout.Tab?) {
                     //nothing
                 }

                 override fun onTabReselected(tab: TabLayout.Tab?) {
                     //nothing
                 }
             })
             btnBack.setOnClickListener {
                 finish()
             }

         }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = (position + 1).toString()
            tab.view.isClickable = false
        }.attach()

        timer = object: CountDownTimer((themeQuestions.size * 20000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val allSeconds = millisUntilFinished/1000
                val minutes = allSeconds/60
                val remainSeconds = allSeconds%60
                var minutesText = minutes.toString()
                var remainSecondsText = remainSeconds.toString()
                if (minutes<10) minutesText = "0$minutes"
                if (remainSeconds<10) remainSecondsText = "0$remainSeconds"
                "$minutesText:$remainSecondsText".also { binding.timerCounter.text = it }
            }

            override fun onFinish() {
                Toast.makeText(this@QuizActivity, getString(R.string.time_over), Toast.LENGTH_SHORT).show()
                finishQuiz()
            }
        }
        timer.start()

    }

    override fun onPause() {
        super.onPause()
        timer.cancel()
    }

    private fun finishQuiz() {
        Toast.makeText(
            this@QuizActivity,
            String.format(getString(R.string.result_answer),testsCount.toString(),testResult.toString()),
            Toast.LENGTH_LONG
        ).show()
        val currentTheme = themeDatabase.themeDao().getAllThemes()[FILE_NUMBER]
        if (testResult>currentTheme.testResult) {
            currentTheme.testsCount = testsCount
            currentTheme.testResult = testResult
            themeDatabase.themeDao().updateTheme(currentTheme)
        }
        finish()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setOnVariantClickListener(correctAnswer: String, isCorrect: Boolean) {

        if (isCorrect) {
            testResult+=1
            binding.failure.visibility = View.GONE
            binding.success.visibility = View.VISIBLE
        } else {
            binding.success.visibility = View.GONE
            binding.failure.visibility = View.VISIBLE
            binding.failureLastAnswer.text =
                String.format(getString(R.string.correct_answer), correctAnswer)
        }

        val h = Handler(Looper.getMainLooper())
        h.postDelayed({
            TransitionManager.beginDelayedTransition(binding.root)
            binding.success.visibility = View.GONE
            binding.failure.visibility = View.GONE

            if (currentPage == themeQuestions.size-1) {
                finishQuiz()
            }
            else{
                binding.viewPager.currentItem = currentPage+1
                binding.viewPager.adapter!!.notifyDataSetChanged()
            }
        }, 2000)

    }

}


