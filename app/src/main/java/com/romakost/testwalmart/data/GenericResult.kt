package com.romakost.testwalmart.data

sealed class GenericResult<T : Any> {
    class Progress<T : Any>: GenericResult<T>()
    class Success<T : Any>(val data: T): GenericResult<T>()
    class Error<T : Any>(val errorMessage: String): GenericResult<T>()
}