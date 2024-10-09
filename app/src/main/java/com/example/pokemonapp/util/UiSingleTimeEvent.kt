package com.example.pokemonapp.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

sealed class UiSingleTimeEvent {
    data object PopBackStack: UiSingleTimeEvent()
    data class Navigate(val route: String): UiSingleTimeEvent()

    companion object{
        fun emitUiSingleTimeEvent(event: UiSingleTimeEvent, scope: CoroutineScope, flow: MutableSharedFlow<UiSingleTimeEvent>){
            scope.launch {
                flow.emit(event)
            }
        }
    }

}