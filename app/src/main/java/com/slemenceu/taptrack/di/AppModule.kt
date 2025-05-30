package com.slemenceu.taptrack.di

import com.slemenceu.taptrack.authentication.data.AuthStatus
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashViewModel
import org.koin.dsl.module


val appModule = module {
    single {AuthStatus(get())}
    single { SplashViewModel(get()) }
}