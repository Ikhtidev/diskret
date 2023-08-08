package uz.ikhtidev.diskret.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.ikhtidev.diskret.database.dao.QuestionDao
import uz.ikhtidev.diskret.database.dao.ThemeDao
import uz.ikhtidev.diskret.database.dao.VariantDao
import uz.ikhtidev.diskret.database.entity.Question
import uz.ikhtidev.diskret.database.entity.Theme
import uz.ikhtidev.diskret.database.entity.Variant

@Database(entities = [Theme::class, Question::class, Variant::class], version = 1)
abstract class ThemeDatabase: RoomDatabase() {
    abstract fun themeDao(): ThemeDao
    abstract fun questionDao(): QuestionDao
    abstract fun variantDao(): VariantDao

    companion object {
        private var INSTANCE: ThemeDatabase? = null

        @Synchronized
        fun getInstance(context: Context): ThemeDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, ThemeDatabase::class.java, "theme_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE!!
        }
    }
}