package com.slemenceu.taptrack.features.connection.domain.models

data class ConnectionResult(
    val latency: Int,
    val deviceName: String,
    val networkName: String
)
