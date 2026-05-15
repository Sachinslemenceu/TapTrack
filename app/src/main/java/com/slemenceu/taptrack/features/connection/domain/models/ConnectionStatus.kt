package com.slemenceu.taptrack.features.connection.domain.models

sealed interface ConnectionStatus {
    data object Disconnected : ConnectionStatus
    data class Connecting(val step: ConnectionStep = ConnectionStep.QR_CODE_SCANNED) : ConnectionStatus
    data object Connected : ConnectionStatus
    data class Failed(val message: String) : ConnectionStatus
}



enum class ConnectionStep{
    QR_CODE_SCANNED,
    ESTABLISHING_UDP_CONNECTION,
    VERIFYING_LATENCY
}

