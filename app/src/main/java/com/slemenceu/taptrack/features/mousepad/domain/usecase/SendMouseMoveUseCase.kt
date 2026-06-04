package com.slemenceu.taptrack.features.mousepad.domain.usecase

import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository

class SendMouseMoveUseCase(
    private val repository: MouseRepository
) {
    suspend operator fun invoke(dx: Int, dy: Int) {
        repository.sendMouseMove(dx, dy)
    }
}