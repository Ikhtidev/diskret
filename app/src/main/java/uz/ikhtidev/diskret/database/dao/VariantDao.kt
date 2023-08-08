package uz.ikhtidev.diskret.database.dao

import androidx.room.*
import uz.ikhtidev.diskret.database.entity.Variant

@Dao
interface VariantDao {

    @Insert
    fun addVariant(variant: Variant)

}