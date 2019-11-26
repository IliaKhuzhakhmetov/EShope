package ru.don.eshope.database.entities.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<T> {

    @Insert
    fun insert(t: T): Long

    @Insert
    fun insert(vararg obj: T): Completable

    @Update
    fun update(t: T): Completable

    @Delete
    fun delete(t: T): Completable


}