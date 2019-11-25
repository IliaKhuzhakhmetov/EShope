package ru.don.eshope.database.repos.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

abstract class BaseRepo : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    suspend fun makeOnIoThread( c: () -> Unit ) {
        withContext(Dispatchers.IO){
            c()
        }
    }
}