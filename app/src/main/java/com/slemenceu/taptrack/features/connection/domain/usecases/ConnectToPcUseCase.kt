package com.slemenceu.taptrack.features.connection.domain.usecases

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext


class ConnectToPcUseCase(
    private val repo: ConnectionRepository
) {
    suspend operator fun invoke(qr: String): Result<Int>{
        return try {
            val parts = qr.split(":")
            if (parts.size != 3) {
                return Result.failure(IllegalArgumentException("Invalid QR format"))
            }

            val ipAddress = parts[0]
            val portNo = parts[1].toInt()
            val passcode = parts[2].toInt()
            repo.connect(ipAddress, portNo)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}