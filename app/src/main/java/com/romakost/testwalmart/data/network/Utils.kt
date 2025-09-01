package com.romakost.testwalmart.data.network

fun Int.errorCodeToExceptionMessage(): String {
    return when (this) {
        400 -> "Something went wrong with your request. Please try again."
        401 -> "You need to log in to continue."
        403 -> "You don’t have permission to do this."
        404 -> "We couldn’t find what you’re looking for."
        408 -> "The request took too long. Please check your connection."
        429 -> "Too many requests. Please wait a moment and try again."
        500 -> "Oops! Something went wrong on our side. Please try later."
        502 -> "The server had a problem. Try again in a bit."
        503 -> "Service is temporarily unavailable. Please try again soon."
        504 -> "The server is taking too long to respond. Try again later."
        else  -> "An unexpected error occurred. Please try again."
    }
}