package com.slemenceu.taptrack.di

import com.slemenceu.taptrack.features.authentication.data.AuthRepositoryImpl
import com.slemenceu.taptrack.features.authentication.data.AuthService
import com.slemenceu.taptrack.features.authentication.data.AuthStatus
import com.slemenceu.taptrack.features.authentication.domain.AuthRepository
import com.slemenceu.taptrack.features.authentication.ui.login_screen.LoginViewModel
import com.slemenceu.taptrack.features.authentication.ui.register_screen.RegisterViewModel
import com.slemenceu.taptrack.features.authentication.ui.reset_password.ResetPasswordViewModel
import com.slemenceu.taptrack.features.authentication.ui.splash_screen.SplashViewModel
import com.slemenceu.taptrack.features.connection.data.repository.ConnectionRepositoryImpl
import com.slemenceu.taptrack.features.connection.data.service.ConnectionManager
import com.slemenceu.taptrack.features.connection.domain.repository.ConnectionRepository
import com.slemenceu.taptrack.features.connection.domain.usecases.ConnectToPcUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.DisconnectUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetConnectionStatusUseCase
import com.slemenceu.taptrack.features.connection.domain.usecases.GetLatencyUseCase
import com.slemenceu.taptrack.features.connection.ui.scanner.ScannerViewModel
import com.slemenceu.taptrack.features.mousepad.data.repository.HomeRepositoryImpl
import com.slemenceu.taptrack.features.mousepad.data.repository.MouseRepositoryImpl
import com.slemenceu.taptrack.features.mousepad.data.repository.QRScannerRepoImpl
import com.slemenceu.taptrack.features.mousepad.data.services.WifiService
import com.slemenceu.taptrack.features.mousepad.domain.HomeRepository
import com.slemenceu.taptrack.features.mousepad.domain.MouseRepository
import com.slemenceu.taptrack.features.mousepad.domain.QRScannerRepo
import com.slemenceu.taptrack.features.mousepad.domain.usecase.SendClickUseCase
import com.slemenceu.taptrack.features.mousepad.domain.usecase.SendMouseMoveUseCase
import com.slemenceu.taptrack.features.mousepad.domain.usecase.SendScrollUseCase
import com.slemenceu.taptrack.features.mousepad.ui.home_screen.HomeViewModel
import com.slemenceu.taptrack.features.mousepad.ui.trackpad_screen.TrackpadViewModel
import com.slemenceu.taptrack.features.mousepad.ui.options_screen.OptionsViewModel
import org.koin.dsl.module


val appModule = module {

    single {AuthStatus(get())}
    single { AuthService() }
    single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    single<HomeRepository> { HomeRepositoryImpl(get()) }
    single<MouseRepository> { MouseRepositoryImpl(get()) }
    single<QRScannerRepo> { QRScannerRepoImpl() }
    single { SplashViewModel(get()) }
    single { HomeViewModel(get(),get(),get()) }
    single { LoginViewModel(get()) }
    single { RegisterViewModel(get()) }
    single { ResetPasswordViewModel(get()) }
    single { WifiService(get()) }
    single{ TrackpadViewModel(get(),get(),get(),get(),get(),get()) }
    single{ OptionsViewModel(get()) }
    single { ScannerViewModel()  }

    single { ConnectToPcUseCase(get()) }
    single<ConnectionRepository> { ConnectionRepositoryImpl(get()) }
    single { ConnectionManager() }
    single { GetConnectionStatusUseCase(get()) }
    single { GetLatencyUseCase(get()) }
    single { DisconnectUseCase(get()) }
    single { SendClickUseCase(get()) }
    single { SendMouseMoveUseCase(get()) }
    single { SendScrollUseCase(get()) }

}
