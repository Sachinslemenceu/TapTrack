package com.slemenceu.taptrack.features.connection.domain.usecases

import com.slemenceu.taptrack.features.connection.domain.models.ConnectionResult
import com.slemenceu.taptrack.features.connection.domain.models.ConnectionStatus
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext


class ConnectToPcUseCase(
    private val repo: ConnectionRepository
) {
    suspend operator fun invoke(qr: String): Result<ConnectionResult>{
        return try {
            val parts = qr.split(":")
            if (parts.size != 5) {
                return Result.failure(IllegalArgumentException("Invalid QR format"))
            }


            val ipAddress = parts[0]
            val portNo = parts[1].toInt()
            val passcode = parts[2].toInt()
            val deviceName= parts[3]
            val networkName= parts[4]
            val result = repo.connect(ipAddress, portNo)
            if (result.isSuccess) {
                Result.success(ConnectionResult(result.getOrNull()!!,deviceName,networkName))
            } else {
                Result.failure(result.exceptionOrNull()!!)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}