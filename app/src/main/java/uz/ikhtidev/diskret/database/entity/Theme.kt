package uz.ikhtidev.diskret.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme")
data class Theme(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "is_read")
    var isRead: Int = 0,
    @ColumnInfo(name = "test_result")
    var testResult: Int = 0,
    @ColumnInfo(name = "tests_count")
    var testsCount: Int = 0
)