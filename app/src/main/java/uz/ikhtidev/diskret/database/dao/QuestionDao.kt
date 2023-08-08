package uz.ikhtidev.diskret.database.dao

import androidx.room.*
import uz.ikhtidev.diskret.database.entity.Question
import uz.ikhtidev.diskret.database.entity.QuestionWithVariant

@Dao
interface QuestionDao {

    @Query("select * from question order by question_id")
    fun getAllQuestions():List<Question>

    @Query("select * from question where theme_id = :themeId")
    fun getAllQuestionsByThemeId(themeId:Long):List<Question>

    @Insert
    fun addQuestion(question: Question): Long

    @Transaction
    @Query("SELECT * FROM question WHERE theme_id = :themeId")
    fun getQuestionsWithVariants(themeId: Int): List<QuestionWithVariant>

}