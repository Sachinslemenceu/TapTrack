package com.slemenceu.taptrack.features.connection.domain.usecases

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import kotlinx.coroutines.flow.StateFlow

class GetLatencyUseCase(
    private val repository: ConnectionRepository
) {
    operator fun invoke(): StateFlow<Int?> {
        return repository.latency
    }
}
