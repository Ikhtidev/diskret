package uz.ikhtidev.diskret.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionWithVariant(
    @Embedded val question: Question,
    @Relation(
        parentColumn = "question_id",
        entityColumn = "question_owner_id"
    )
    var variants: List<Variant>
)
