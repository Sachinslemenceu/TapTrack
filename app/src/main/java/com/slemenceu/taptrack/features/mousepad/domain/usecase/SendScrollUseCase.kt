package com.slemenceu.taptrack.features.mousepad.domain.usecase

import android.util.Log
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository

class SendScrollUseCase(
    private val repository: MouseRepository
) {
    suspend operator fun invoke(dy: Int) {
        Log.d("SendScrollUseCase", "Sending scroll: $dy")
        repository.sendScroll(dy)
    }
}