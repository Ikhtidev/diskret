package uz.ikhtidev.diskret.database.dao

import androidx.room.*
import uz.ikhtidev.diskret.database.entity.Theme

@Dao
interface ThemeDao {

    @Query("select * from theme order by id")
    fun getAllThemes():List<Theme>

    @Query("SELECT * FROM theme WHERE tests_count!=:testsCount")
    fun getResultThemes(testsCount:Int?=0):List<Theme>

    @Insert
    fun addTheme(theme:Theme)

    @Update
    fun updateTheme(theme: Theme)

}