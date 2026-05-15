package com.slemenceu.taptrack.features.connection.domain.usecases


import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.StateFlow

class GetConnectionStatusUseCase(
    private val repository: ConnectionRepository
) {
    operator fun invoke(): StateFlow<ConnectionStatus> {
        return repository.connectionStatus
    }
}