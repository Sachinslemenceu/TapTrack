package com.slemenceu.taptrack.features.connection.domain.usecases

import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository

class DisconnectUseCase(
    private val repository: ConnectionRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            repository.disconnect()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
