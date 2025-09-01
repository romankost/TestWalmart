package com.romakost.testwalmart.data

import com.romakost.testwalmart.data.network.errorCodeToExceptionMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun `400 returns bad request message`() {
        assertEquals(
            "Something went wrong with your request. Please try again.",
            400.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `401 returns unauthorized message`() {
        assertEquals(
            "You need to log in to continue.",
            401.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `403 returns forbidden message`() {
        assertEquals(
            "You don’t have permission to do this.",
            403.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `404 returns not found message`() {
        assertEquals(
            "We couldn’t find what you’re looking for.",
            404.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `408 returns timeout message`() {
        assertEquals(
            "The request took too long. Please check your connection.",
            408.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `429 returns too many requests message`() {
        assertEquals(
            "Too many requests. Please wait a moment and try again.",
            429.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `500 returns internal server error message`() {
        assertEquals(
            "Oops! Something went wrong on our side. Please try later.",
            500.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `502 returns bad gateway message`() {
        assertEquals(
            "The server had a problem. Try again in a bit.",
            502.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `503 returns service unavailable message`() {
        assertEquals(
            "Service is temporarily unavailable. Please try again soon.",
            503.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `504 returns gateway timeout message`() {
        assertEquals(
            "The server is taking too long to respond. Try again later.",
            504.errorCodeToExceptionMessage()
        )
    }

    @Test
    fun `unknown code returns default message`() {
        assertEquals(
            "Please check your internet connection and try again.",
            999.errorCodeToExceptionMessage()
        )
    }
}