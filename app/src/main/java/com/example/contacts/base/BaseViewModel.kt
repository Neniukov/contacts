package com.example.contacts.base

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import retrofit2.HttpException

abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val dispatcher = Dispatchers.Main.immediate
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        progressVisible.value = false
        handleError(e)
    }

    private val job = SupervisorJob()

    final override val coroutineContext = dispatcher + job + coroutineExceptionHandler

    val progressVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val errorFlow = MutableStateFlow<String?>(null)

    override fun onCleared() {
        coroutineContext.cancelChildren()
        super.onCleared()
    }

    private fun handleError(e: Throwable) {
        if (e is HttpException) {
            val string = e.response()?.errorBody()?.string()
            val body = string?.let {
                val parsed = JsonParser.parseString(it)
                if (parsed is JsonObject) parsed.asJsonObject?.get(MESSAGE)?.asString else it
            }
            errorFlow.value = body ?: e.message()
        } else {
            errorFlow.value = e.message
        }
    }

    protected fun <T> Flow<T>.invokeWithDialog(): Flow<T> {
        progressVisible.value = true
        return this
    }

    protected suspend inline fun <T> Flow<T>.collectWithDialog(crossinline action: suspend (value: T) -> Unit) =
        this.collect {
            progressVisible.value = false
            action(it)
        }

    companion object {
        private const val MESSAGE = "message"
    }
}