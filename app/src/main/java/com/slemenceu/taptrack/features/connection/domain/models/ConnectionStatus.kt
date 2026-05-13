package com.slemenceu.taptrack.features.connection.domain.models

sealed interface ConnectionStatus {
    data object Disconnected : ConnectionStatus
    data object Connecting : ConnectionStatus
    data object Connected : ConnectionStatus
    data class Failed(val message: String) : ConnectionStatus
}