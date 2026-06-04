package com.slemenceu.taptrack.features.mousepad.domain.usecase

import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository

class SendClickUseCase(
    private val repository: MouseRepository
) {
    suspend operator fun invoke(isRightClick: Boolean) {
        repository.sendClick(isRightClick)
    }
}