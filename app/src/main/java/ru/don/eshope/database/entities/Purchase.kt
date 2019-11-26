package ru.don.eshope.database.entities

import androidx.room.*
import io.reactivex.Single
import ru.don.eshope.database.entities.base.BaseDao

@Entity(tableName = "purchase_table")
data class Purchase(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "shop_name") val name: String,
    @ColumnInfo(name = "purchase_date") val date: String,
    @ColumnInfo(name = "purchase_amount") var amount: Double
)

data class PurchaseAndItems(
    val id: Int,
    @ColumnInfo(name = "shop_name") val name: String,
    @ColumnInfo(name = "purchase_date") val date: String,
    @ColumnInfo(name = "purchase_amount") val amount: Double,
    @Relation(parentColumn = "id", entityColumn = "purchase_id") val items: List<Item>
)

@Dao
interface PurchaseDao : BaseDao<Purchase> {

    @Query("DELETE FROM purchase_table")
    fun deleteAllPurchases()

    @Transaction
    @Query("SELECT * FROM purchase_table WHERE id=:id ")
    fun getById(id: Int): Single<PurchaseAndItems>

    @Query("SELECT * FROM purchase_table ORDER BY id DESC")
    fun getAllPurchases(): Single<List<Purchase>>

}
