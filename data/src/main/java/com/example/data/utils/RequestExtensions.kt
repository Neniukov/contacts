package com.example.data.utils

import kotlinx.coroutines.flow.flow

inline fun <T> typeFlow(crossinline block: suspend () -> T) = flow { emit(block()) }