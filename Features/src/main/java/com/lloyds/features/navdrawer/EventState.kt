package com.lloyds.features.navdrawer

sealed interface EventState {

    data object Shows : EventState
    data object Cast : EventState
}