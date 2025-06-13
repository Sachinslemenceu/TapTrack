package com.slemenceu.taptrack.mousepad.domain

interface MouseRepository {
    suspend fun connectToMousepad(passcode: Int): Boolean
    suspend fun disconnectFromMousePad()
}