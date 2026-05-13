package com.slemenceu.taptrack.features.mousepad.ui.options_screen

sealed class OptionsUiEvent {
    object OnBackClicked: OptionsUiEvent()
    object OnLogoutClicked: OptionsUiEvent()
    object OnUnfinishedFeatureClicked: OptionsUiEvent()
}