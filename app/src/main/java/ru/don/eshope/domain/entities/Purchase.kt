package ru.don.eshope.domain.entities

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single

@Entity(tableName = "purchase_table")
data class Purchase(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "shop_name") val name: String,
    @ColumnInfo(name = "purchase_date") val date: String,
    @ColumnInfo(name = "purchase_amount") var amount: Double
)

@Dao
interface PurchaseDao {

    @Insert
    fun insert(purchase: Purchase): Completable

    @Update
    fun update(purchase: Purchase): Completable

    @Delete
    fun delete(purchase: Purchase): Completable

    @Query("DELETE FROM purchase_table")
    fun deleteAllPurchases()

    @Query("SELECT * FROM purchase_table ORDER BY id DESC")
    fun getAllPurchases(): Single<List<Purchase>>

}
