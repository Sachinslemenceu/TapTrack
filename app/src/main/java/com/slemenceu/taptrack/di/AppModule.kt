package com.slemenceu.taptrack.di

import com.slemenceu.taptrack.authentication.data.AuthRepositoryImpl
import com.slemenceu.taptrack.authentication.data.AuthService
import com.slemenceu.taptrack.authentication.data.AuthStatus
import com.slemenceu.taptrack.authentication.domain.AuthRepository
import com.slemenceu.taptrack.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.authentication.ui.splash_screen.SplashViewModel
import com.slemenceu.taptrack.mousepad.data.repository.HomeRepositoryImpl
import com.slemenceu.taptrack.mousepad.data.repository.MouseRepositoryImpl
import com.slemenceu.taptrack.mousepad.data.repository.QRScannerRepoImpl
import com.slemenceu.taptrack.mousepad.data.services.WifiService
import com.slemenceu.taptrack.mousepad.domain.HomeRepository
import com.slemenceu.taptrack.mousepad.domain.MouseRepository
import com.slemenceu.taptrack.mousepad.domain.QRScannerRepo
import com.slemenceu.taptrack.mousepad.ui.home_screen.HomeViewModel
import com.slemenceu.taptrack.mousepad.ui.mousepad_screen.MouseViewModel
import org.koin.dsl.module


val appModule = module {
    single {AuthStatus(get())}
    single { AuthService() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<MouseRepository> { MouseRepositoryImpl() }
    single<QRScannerRepo> { QRScannerRepoImpl() }
    single { SplashViewModel(get()) }
    single { HomeViewModel(get(), get(),get(),get()) }
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { WifiService(get()) }
    single{ MouseViewModel(get()) }
}