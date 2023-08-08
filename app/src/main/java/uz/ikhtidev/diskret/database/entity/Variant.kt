package uz.ikhtidev.diskret.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "variant",
    foreignKeys = [ForeignKey(
        entity = Question::class,
        parentColumns = ["question_id"],
        childColumns = ["question_owner_id"],
        onDelete = CASCADE
    )]
)
data class Variant(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    @ColumnInfo(name = "is_true")
    val isTrue: Boolean = false,
    @ColumnInfo(name = "question_owner_id")
    val questionOwnerId: Long
)
