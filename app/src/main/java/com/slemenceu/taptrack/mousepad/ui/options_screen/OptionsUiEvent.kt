package com.slemenceu.taptrack.mousepad.ui.options_screen

sealed class OptionsUiEvent {
    object OnBackClicked: OptionsUiEvent()
    object OnLogoutClicked: OptionsUiEvent()
    object OnUnfinishedFeatureClicked: OptionsUiEvent()
}