package com.slemenceu.taptrack.di

import com.slemenceu.taptrack.authentication.data.AuthService
import com.slemenceu.taptrack.authentication.data.AuthStatus
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashViewModel
import org.koin.dsl.module


val appModule = module {
    single {AuthStatus(get())}
    single { AuthService() }

    single { SplashViewModel(get()) }
    single { LoginViewModel(get(),get()) }
    single { RegisterViewModel(get(),get()) }

}