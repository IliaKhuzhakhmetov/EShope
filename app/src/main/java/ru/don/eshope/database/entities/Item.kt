package ru.don.eshope.database.entities

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import io.reactivex.Single
import ru.don.eshope.database.entities.base.BaseDao

@Entity(
    tableName = "item_table", foreignKeys = [ForeignKey(
        entity = Purchase::class,
        parentColumns = ["id"],
        childColumns = ["purchase_id"],
        onDelete = CASCADE
    )]
)
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    @ColumnInfo(name = "item_name") val name: String,
    @ColumnInfo(name = "item_count") var count: Int,
    @ColumnInfo(name = "item_price") var price: Double,
    @ColumnInfo(name = "purchase_id") var purchaseId: Int?
)

@Dao
interface ItemDao : BaseDao<Item> {

    @Query("SELECT * FROM item_table WHERE purchase_id=:id ")
    fun getById(id: Int): Single<List<Item>>

    @Query("SELECT * FROM item_table ORDER BY id DESC")
    fun getAllItems(): Single<List<Item>>

}
